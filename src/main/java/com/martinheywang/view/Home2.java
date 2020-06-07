package com.martinheywang.view;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import com.martinheywang.Main;
import com.martinheywang.model.Game;
import com.martinheywang.model.database.Database;

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
			List<Game> games = Database.createDao(Game.class).queryForAll();
			games.sort(Comparator.comparing(Game::getLastSave).reversed());

			for (Game game : games) {
				Displayer<Game> displayer = game.getDisplayer();
				displayer.addHoverEffect();
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
			e.printStackTrace();
		}
	}
}
