package com.martin.view;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.Partie;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Accueil2Contrôle {
	
	@FXML VBox listePartie;
	@FXML TextField field;
	Main main;

	@FXML
	private void nouvelle() {
		if(!field.getText().isEmpty()) {
			try {
				Partie partie = new Partie(field.getText());
				main.initGame(partie);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <h1>setMainApp</h1>
	 * <p>Sets the object main</p>
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;
		try {
			for(Partie partie : Connect_SQLite.getPartieDao().queryForAll()) {
				Displayer displayer = new Displayer(partie);
				listePartie.getChildren().add(displayer);
				displayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						try {
							main.initGame(((Displayer) event.getSource()).getPartie());
						} catch (Exception e) {
							e.printStackTrace();
							
						}
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
}
