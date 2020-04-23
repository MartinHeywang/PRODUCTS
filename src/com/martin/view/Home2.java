package com.martin.view;

import java.sql.SQLException;

import com.martin.Database;
import com.martin.Main;
import com.martin.model.Game;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Home2 {

	@FXML
	VBox listePartie;
	@FXML
	TextField field;
	Main main;

	@FXML
	private void nouvelle() {
		if (!field.getText().isEmpty()) {
			try {
				Game game = new Game(field.getText());
				main.initGame(game);
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

		try {
			for (Game game : Database.daoGame().queryForAll()) {
				Displayer displayer = new Displayer(game);
				listePartie.getChildren().add(displayer);

				displayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						main.initGame(game);
					}
				});
			}
		} catch (SQLException e) {
			System.err.println(
					"Couldn't load the list from the table games. Here is the full error message:\n\n\n");

		}
	}
}