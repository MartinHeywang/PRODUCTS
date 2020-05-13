package com.martinheywang.view;

import com.martinheywang.Main;
import com.martinheywang.model.Game;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Home {

	@FXML
	TextField field;
	@FXML
	ImageView image;

	Main main;

	/**
	 * <h1>seConnecter</h1>
	 * <p>
	 * Launches the real game and registers the login in the database
	 * </p>
	 */
	@FXML
	public void seConnecter() {
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
	 * Sets the object main and initialize the data in the widgets.
	 * </p>
	 * 
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;
		image.setImage(new Image(getClass().getResourceAsStream("/Logo.png")));
	}

}
