package com.martin.view;

import java.io.File;
import java.io.FileInputStream;

import com.martin.model.Partie;
import com.martin.model.Ressource;
import com.martin.model.appareils.Type;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Displayer extends BorderPane {

	private Label nom;
	private ImageView image;
	private Label infos;

	private Ressource ressource;
	private Type type;
	private Partie partie;

	/**
	 * <h1>Displayer</h1>
	 * <p>
	 * Create an object who displays the object in parameter.
	 * </p>
	 * 
	 * @param ressource the object to display
	 */
	public Displayer(Ressource ressource) {

		try {
			this.ressource = ressource;

			nom = new Label();
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(ressource.getNom());
			this.setTop(nom);

			image = new ImageView();
			image.setImage(new Image(
					new FileInputStream(new File(ressource.getURL()))));
			this.setCenter(image);

			infos = new Label();
			infos.setAlignment(Pos.TOP_CENTER);
			infos.setText("Prix de vente : "
					+ String.valueOf(ressource.getValue()) + " €");
			this.setBottom(infos);

		} catch (Exception e) {
			System.out.println(
					"ERREUR lors de la création d'un Displayer à partir d'une Ressource. "
							+ "Raison :\n" + e.getMessage());
		}
	}

	/**
	 * <h1>Displayer</h1>
	 * <p>
	 * Creates an object who displays the object in parameter
	 * </p>
	 * 
	 * @param appareil the object to display
	 */
	public Displayer(Type appareil) {
		try {
			this.type = appareil;

			nom = new Label();
			nom.setUnderline(true);
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(appareil.getNom());
			nom.setWrapText(true);
			this.setTop(nom);

			image = new ImageView();
			image.setImage(new Image(new FileInputStream(new File(
					"images/machines niveau 1/" + appareil.getURL()))));
			this.setRight(image);

			infos = new Label();
			infos.setAlignment(Pos.TOP_CENTER);
			infos.setText(
					"Prix de construction : " + appareil.getPrix() + " €\n\n"
							+ appareil.getDescription());
			infos.setWrapText(true);
			this.setLeft(infos);

			this.setPadding(new Insets(3, 3, 3, 3));

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				final String style = "-fx-background-color: #3b4044;";

				((Displayer) e.getSource()).setStyle(style);
				nom.setStyle(style);
				infos.setStyle(style);

			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				final String style = "-fx-background-color: #242729;";

				((Displayer) e.getSource()).setStyle(style);
				nom.setStyle(style);
				infos.setStyle(style);

			}
		});
	}

	/**
	 * <h1>Displayer</h1>
	 * <p>
	 * Creat an object who displays the object in parameter
	 * </p>
	 * 
	 * @param appareil the object to display
	 */
	public Displayer(Partie partie) {
		try {
			this.partie = partie;
			this.setMinHeight(70.0);

			nom = new Label();
			nom.setUnderline(true);
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(partie.getNom());
			nom.setWrapText(true);
			this.setTop(nom);

			// Changing the LocalDateTime of the game in a String representation
			// Instead of using toString(), i ues this method to have my perso
			// String
			String instant = (partie.getLastView().getDayOfMonth() < 10)
					? "0" + partie.getLastView().getDayOfMonth() + "/"
					: partie.getLastView().getDayOfMonth() + "/";
			instant += (partie.getLastView().getMonthValue() < 10)
					? "0" + partie.getLastView().getMonthValue() + "/"
					: partie.getLastView().getMonthValue() + "/";
			instant += (partie.getLastView().getYear() < 10)
					? "0" + partie.getLastView().getYear() + " "
					: partie.getLastView().getYear() + " ";

			instant += (partie.getLastView().getHour() < 10)
					? "0" + partie.getLastView().getHour() + ":"
					: partie.getLastView().getHour() + ":";
			instant += (partie.getLastView().getMinute() < 10)
					? "0" + partie.getLastView().getMinute() + ":"
					: partie.getLastView().getMinute() + ":";
			instant += (partie.getLastView().getSecond() < 10)
					? "0" + partie.getLastView().getSecond()
					: partie.getLastView().getSecond();

			infos = new Label();
			infos.setText("Dernière sauvegarde : " + instant
					+ "\nArgent en compte : "
					+ partie.getArgent() + " €");
			this.setLeft(infos);

			this.setPadding(new Insets(3, 3, 3, 3));

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				final String style = "-fx-background-color: #3b4044;";

				((Displayer) e.getSource()).setStyle(style);
				nom.setStyle(style);
				infos.setStyle(style);

			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				final String style = "-fx-background-color: #242729;";

				((Displayer) e.getSource()).setStyle(style);
				nom.setStyle(style);
				infos.setStyle(style);

			}
		});
	}

	/**
	 * @return ressource the displayed resources
	 * @throws NullPointerException if the property Ressource is null
	 *                              (this object wasn't created from a
	 *                              Ressource)
	 */
	public Ressource getRessource() throws NullPointerException {
		return ressource;
	}

	/**
	 * @return typeAppareil the displayed device
	 * @throws NullPointerException if the property TypeAppareil is null
	 *                              (this object wasn't created from a
	 *                              TypeAppareil)
	 */
	public Type getTypeAppareil() throws NullPointerException {
		return type;
	}

	/**
	 * @return partie the displayed game
	 * @throws NullPointerException if the property Partie is null (this
	 *                              object wasn't created from a Partie)
	 */
	public Partie getPartie() throws NullPointerException {
		return partie;
	}
}
