package com.martin.view;

import com.martin.Main;
import com.martin.model.Partie;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Accueil2Contrôle {

	@FXML
	VBox listePartie;
	@FXML
	TextField field;
	Main main;

	@FXML
	private void nouvelle() {
		if (!field.getText().isEmpty()) {
			try {
				Partie partie = new Partie(field.getText());
				main.initGame(partie);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <h1>setMainApp</h1>
	 * <p>
	 * Sets the object main
	 * </p>
	 * 
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;

		for (Partie partie : Partie.query()) {
			Displayer displayer = new Displayer(partie);
			listePartie.getChildren().add(displayer);

			displayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					main.initGame(partie);
				}
			});
		}
	}
}
