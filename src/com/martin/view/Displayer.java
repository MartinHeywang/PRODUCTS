package com.martin.view;

import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.Locale;

import com.martin.model.Game;
import com.martin.model.Resource;
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

	private Resource resource;
	private Type type;
	private Game game;

	/**
	 * Creates a Displayer.
	 * 
	 * @param resource the object to display
	 */
	public Displayer(Resource resource) {

		try {
			this.resource = resource;

			nom = new Label();
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(resource.getNom());
			this.setTop(nom);

			image = new ImageView();
			image.setImage(new Image(
					new FileInputStream(new File(resource.getURL()))));
			this.setCenter(image);

			infos = new Label();
			infos.setAlignment(Pos.TOP_CENTER);
			infos.setText("Prix de vente : "
					+ String.valueOf(resource.getValue()) + " €");
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
	public Displayer(Game game) {
		try {
			this.game = game;
			this.setMinHeight(70.0);

			nom = new Label();
			nom.setUnderline(true);
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(game.getNom());
			nom.setWrapText(true);
			this.setTop(nom);

			// Changing the LocalDateTime of the game in a String representation
			// Instead of using toString(), i ues this method to have my perso
			// String
			String instant = (game.getLastView().getDayOfMonth() < 10)
					? "0" + game.getLastView().getDayOfMonth() + "/"
					: game.getLastView().getDayOfMonth() + "/";
			instant += (game.getLastView().getMonthValue() < 10)
					? "0" + game.getLastView().getMonthValue() + "/"
					: game.getLastView().getMonthValue() + "/";
			instant += (game.getLastView().getYear() < 10)
					? "0" + game.getLastView().getYear() + " "
					: game.getLastView().getYear() + " ";

			instant += (game.getLastView().getHour() < 10)
					? "0" + game.getLastView().getHour() + ":"
					: game.getLastView().getHour() + ":";
			instant += (game.getLastView().getMinute() < 10)
					? "0" + game.getLastView().getMinute() + ":"
					: game.getLastView().getMinute() + ":";
			instant += (game.getLastView().getSecond() < 10)
					? "0" + game.getLastView().getSecond()
					: game.getLastView().getSecond();

			infos = new Label();
			infos.setText("Dernière sauvegarde : " + instant
					+ "\nArgent en compte : "
					+ NumberFormat.getInstance(Locale.getDefault())
							.format(game.getArgent())
					+ " €");
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
	public Resource getRessource() throws NullPointerException {
		return resource;
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
	public Game getPartie() throws NullPointerException {
		return game;
	}
}
