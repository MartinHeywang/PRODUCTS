package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.Partie;
import com.martin.model.Coordonnées;
import com.martin.model.LocatedImage;
import com.martin.model.Stock;
import com.martin.model.appareils.comportement.Comportement;
import com.martin.model.appareils.comportement.Comportement_Aucun;
import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Entrées_Aucune;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.model.appareils.orientation.Sorties_Aucune;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.AppareilsContrôle;
import com.martin.view.Dashboard;
import com.martin.view.JeuContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

@DatabaseTable(tableName = "appareils")
public class Appareil extends ImageView {

	@DatabaseField(generatedId = true, canBeNull = false, unique = true)
	protected int idAppareil;

	@DatabaseField(foreign = true, foreignColumnName = "idPartie", uniqueCombo = true)
	protected Partie partie;

	@DatabaseField
	protected TypeAppareil type;
	@DatabaseField
	protected Direction direction;
	@DatabaseField
	protected NiveauAppareil niveau;
	@DatabaseField(foreign = true, foreignColumnName = "idCoordonnées", uniqueCombo = true)
	protected Coordonnées xy;

	protected Comportement comportement = new Comportement_Aucun();
	protected Sorties sorties = new Sorties_Aucune();
	protected Entrées entrées = new Entrées_Aucune();

	protected JeuContrôle controller;

	protected static int électricité = 5;
	protected Direction pointerExit;
	protected ArrayList<Direction> pointersEnters;

	protected Dashboard dashboard = new Dashboard();

	public Appareil() {
	}

	/**
	 * @author Martin 26 janv. 2020 | 11:30:04
	 * 
	 *         <b> constructor Appareil</b>
	 *         <p>
	 *         Creates a device.
	 *         </p>
	 * 
	 *         Args :
	 * @param xy         the coordinate or this device
	 * @param type       the kind of the device
	 * @param direction  the rotate of this device
	 * @param niveau     the level of this device
	 * @param controller the game controller
	 * 
	 */
	protected Appareil(Coordonnées xy, TypeAppareil type, Direction direction,
			NiveauAppareil niveau,
			JeuContrôle controller) throws FileNotFoundException {
		super(new LocatedImage(niveau.getURL() + type.getURL()));

		this.type = type;
		this.direction = direction;
		this.niveau = niveau;
		this.controller = controller;
		if (controller != null)
			this.partie = controller.getPartieEnCours();

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					// Charger et ouvrir la boîte de dialogue demandant l'action
					// à effectuer
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(
							Main.class.getResource("view/Appareil.fxml"));

					Dialog<TypeAppareil> dialog;
					DialogPane dialogPane;

					dialogPane = (DialogPane) loader.load();
					dialog = new Dialog<TypeAppareil>();
					dialog.setTitle("Sélection d'appareil - PRODUCTS.");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.stage);
					dialog.initModality(Modality.NONE);

					AppareilsContrôle controller = loader.getController();
					controller.setMainApp(xy.getX(), xy.getY(), dashboard);

					dialog.showAndWait();

				} catch (IOException e) {
					/*
					 * Si une erreur est survenue lors du chargement de la
					 * fenêtre, afficher le message plus la raison donnée par
					 * Java.
					 */
					System.err.println(
							"ERREUR dans Appareil.java entre les lignes 59 et 79 excluses."
									+ "Raison :\n" + e.getMessage());
				}
			}
		});

		try {
			if (Connect_SQLite
					.getCoordonnéesDao()
					.queryBuilder()
					.where()
					.eq("x", xy.getX())
					.and()
					.eq("y", xy.getY())
					.query()
					.size() == 0)
				Connect_SQLite.getCoordonnéesDao().create(xy);

			this.xy = Connect_SQLite.getCoordonnéesDao().queryBuilder().where()
					.eq("x", xy.getX()).and()
					.eq("y", xy.getY()).query().get(0);
			this.save();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * <h1>action</h1>
	 * <p>
	 * This method do the action of the device. It calls the defined
	 * behaviour.
	 * </p>
	 * 
	 * @param resATraiter the resource who will be used by this device
	 */
	public void action(Stock resATraiter) throws NegativeArgentException {
		if (this.controller
				.getPartieEnCours().getAppareil(
						new Coordonnées(xy.getX() + pointerExit.getxPlus(),
								xy.getY() + pointerExit.getyPlus()))
				.getXy().isNearFrom(xy)) {

			for (int i = 0; i < pointersEnters.size(); i++) {
				if (pointerExit.equals(controller
						.getPartieEnCours().getAppareil(new Coordonnées(
								xy.getX() + pointerExit.getxPlus(),
								xy.getY() + pointerExit.getyPlus()))
						.getPointerEnter().get(i)))

					comportement.action(resATraiter);
			}
		}
	}

	public void save() {
		try {
			Connect_SQLite.getAppareilDao().update(this);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * This method resets the database at the coordinates, and do the
	 * necessary to destruct properly this device.
	 * 
	 */
	public void destruction() {
		try {
			Connect_SQLite.getAppareilDao().updateId(
					new Appareil_Sol(xy, Direction.UP, NiveauAppareil.NIVEAU_1,
							controller),
					idAppareil);
		} catch (Exception e) {

		}
	}

	public Appareil toInstance(JeuContrôle controller)
			throws NullPointerException {
		try {
			return this.getType().getClasse()
					.getConstructor(Coordonnées.class, Direction.class,
							NiveauAppareil.class, JeuContrôle.class)
					.newInstance(this.xy, this.direction, this.niveau,
							controller);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// GETTERS, THEN SETTERS
	public int getID() {
		return idAppareil;
	}

	/**
	 * @return the type
	 */
	public TypeAppareil getType() {
		return type;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * 
	 * @return the pointerEnter
	 */
	public ArrayList<Direction> getPointerEnter() {
		return pointersEnters;
	}

	/**
	 * 
	 * @return the pointerExit
	 */
	public Direction getPointerExit() {
		return pointerExit;
	}

	/**
	 * @return the niveau
	 */
	public NiveauAppareil getNiveau() {
		return niveau;
	}

	/**
	 * @return the xy
	 */
	public Coordonnées getXy() {
		return xy;
	}

	/**
	 * @return the comportement
	 */
	public Comportement getComportement() {
		return comportement;
	}

	/**
	 * @return the sorties
	 */
	public Sorties getSorties() {
		return sorties;
	}

	/**
	 * @return the entrées
	 */
	public Entrées getEntrées() {
		return entrées;
	}

	/**
	 * @return the controller
	 */
	public JeuContrôle getController() {
		return controller;
	}

	/**
	 * @return the électricité
	 */
	public static int getÉlectricité() {
		return électricité;
	}

	/**
	 * 
	 * @return partie the partie property
	 */
	public Partie getPartie() {
		return partie;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeAppareil type) {
		this.type = type;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(NiveauAppareil niveau) {
		this.niveau = niveau;
	}

	/**
	 * @param xy the xy to set
	 */
	public void setXy(Coordonnées xy) {
		this.xy = xy;
	}

	/**
	 * @param comportement the comportement to set
	 */
	public void setComportement(Comportement comportement) {
		this.comportement = comportement;
	}

	/**
	 * @param sorties the sorties to set
	 */
	public void setSorties(Sorties sorties) {
		this.sorties = sorties;
	}

	/**
	 * @param entrées the entrées to set
	 */
	public void setEntrées(Entrées entrées) {
		this.entrées = entrées;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(JeuContrôle controller) {
		this.controller = controller;
	}

	/**
	 * 
	 * @param partie the game to set
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	/**
	 * @param électricité the électricité to set
	 */
	public static void setÉlectricité(int électricité) {
		Appareil.électricité = électricité;
	}

}
