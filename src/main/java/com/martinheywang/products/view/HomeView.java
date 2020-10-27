package com.martinheywang.products.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class HomeView implements Initializable {

    @FXML
    TextField field;

    Main main;

    @FXML
    public void create() {
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
     * Sets the object main and initialize the data in the widgets.
     * 
     * @param main the object to set
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

}
