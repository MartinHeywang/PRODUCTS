package com.martinheywang.products.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.database.Database;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Home2 implements Initializable {

    @FXML
    VBox listePartie;
    @FXML
    TextField field;

    Main main;

    @FXML
    private void create() {
	if (!field.getText().isEmpty()) {
	    try {
		final Game game = new Game(field.getText());
		game.save();
		main.initGame(game);
	    } catch (final Exception e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
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
	    listePartie.getStyleClass().add("selection-box");
	    listePartie.setMinHeight(150d);

	    final List<Game> games = Database.createDao(Game.class).queryForAll();
	    games.sort(Comparator.comparing(Game::getLastSave).reversed());

	    for (final Game game : games) {
		final Displayer<Game> displayer = game.getDisplayer();
		displayer.addHoverEffect();
		displayer.setMinHeight(70d);
		displayer.getStyleClass().add("selectable");
		listePartie.getChildren().add(displayer);

		displayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
			main.initGame(game);
		    }
		});
	    }
	} catch (final SQLException e) {
	    System.err.println(
		    "Couldn't load the list from the table games. Here is the full error message:\n\n\n");
	    e.printStackTrace();
	}
    }

}