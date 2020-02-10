package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.model.Coordonnées;
import com.martin.model.LocatedImage;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement;
import com.martin.model.appareils.comportement.Comportement_Aucun;
import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Entrées_Aucune;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.model.appareils.orientation.Sorties_Aucune;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.AppareilsContrôle;
import com.martin.view.JeuContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

public abstract class Appareil extends ImageView{
	
	// FAIRE les méthodes initializeData()
	// FAIRE les méthodes getPrix()
	// FAIRE les méthodes destructions()
	// FAIRE les listes publiques statiques de coordonnées de référencement des appareils
	
	protected TypeAppareil type;
	protected Direction direction;
	protected NiveauAppareil niveau;
	protected Coordonnées xy;
	
	protected Comportement comportement = new Comportement_Aucun();
	protected Sorties sorties = new Sorties_Aucune();
	protected Entrées entrées = new Entrées_Aucune();
	
	protected JeuContrôle controller;
	
	protected static int électricité = 5;
	protected Direction pointerExit;
	protected Direction pointerEnter;
	
	
	/**
	 * @author Martin
	 * 26 janv. 2020 | 11:30:04
	 * 
	 * <b> constructor Appareil</b>
	 * <p>Creates a device.</p>
	 * 
	 * Args :
	 * @param xy the coordinate or this device
	 * @param type the kind of the device
	 * @param direction the rotate of this device
	 * @param niveau the level of this device
	 * @param controller the game controller
	 * 
	*/
	protected Appareil(Coordonnées xy, TypeAppareil type, Direction direction, NiveauAppareil niveau,
			JeuContrôle controller) throws FileNotFoundException {
		super(new LocatedImage(niveau.getURL()+type.getURL()));
		
		this.xy = xy;
		this.type = type;
		this.direction = direction;
		this.niveau = niveau;
		this.controller = controller;
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					//Charger et ouvrir la boîte de dialogue demandant l'action à effectuer
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/Appareil.fxml"));
					
					Dialog<TypeAppareil> dialog;
					DialogPane dialogPane;
					
					dialogPane = (DialogPane) loader.load();
					dialog = new Dialog<TypeAppareil>();
					dialog.setTitle("Sélection d'appareil - PRODUCTS.");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.stage);
					dialog.initModality(Modality.NONE);
					
					AppareilsContrôle controller = loader.getController();
					controller.setMainApp(xy.getX(), xy.getY());
					
					dialog.showAndWait();
					
				}catch(IOException e) {
					/*Si une erreur est survenue lors du chargement de la fenêtre,
					 afficher le message plus la raison donnée par Java.*/
					System.out.println("ERREUR dans Appareil.java entre les lignes 59 et 79 excluses."
							+ "Raison :\n"+e.getMessage());
				}
			}
		});
		try {
			Connect_SQLite.getInstance().createStatement()
			.executeUpdate("UPDATE appareils SET '"+(xy.getX()+1)+"' = "
					+ "'"+type.toString()+"*"+niveau.toString()+"|"+direction.toString()+"' WHERE id = "+(xy.getY()+1));
		} catch (SQLException e) {
			controller.setReport("L'appareil n'a pas pu être enregistré dans la base de données.", Color.DARKRED);
			
		}
	}
	/**
	 * <h1>action</h1>
	 * <p>This method do the action of the device. It calls the defined behaviour.</p>
	 * @param resATraiter the resource who will be used by this device
	 */
	public void action(Ressource resATraiter) throws NegativeArgentException{
		if(this.controller.getGrilleAppareils(new Coordonnées(xy.getX()+pointerExit.getxPlus(), 
				xy.getY()+pointerExit.getyPlus())).getXy().isNearFrom(xy)){
			
			if(pointerExit.equals(controller.getGrilleAppareils(new Coordonnées(xy.getX()+pointerExit.getxPlus()
					, xy.getY()+pointerExit.getyPlus())).getPointerEnter()) || this 
					instanceof Appareil_Vendeur)
				comportement.action(resATraiter);
		}
	}
	
	/**
	 * <h1>destruction</h1>
	 * <p>This methode resets the database at the coordinates, and do the necessary to destruct properly this device</p>
	 * 
	*/
	public void destruction(){
		try {
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE appareils SET '"+(xy.getX()+1)+"' = \"SOL*NIVEAU_1|UP\" WHERE id = "+(xy.getY()+1)+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE appareils_infos SET '"+(xy.getX()+1)+"' = \"NONE\" WHERE id = "+(xy.getY()+1)+";");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS, THEN SETTERS
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
	public Direction getPointerEnter() {
		return pointerEnter;
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
	 * @param électricité the électricité to set
	 */
	public static void setÉlectricité(int électricité) {
		Appareil.électricité = électricité;
	}
	
	
	
}
