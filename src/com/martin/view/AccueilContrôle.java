package com.martin.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.martin.Main;
import com.martin.Partie;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AccueilContrôle {
	
	@FXML TextField field;
	@FXML ImageView image;
	
	Main main;
	
	/**
	 * <h1>seConnecter</h1>
	 * <p>Launches the real game and registers the login in the database</p>	 
	 * */
	@FXML
	public void seConnecter() {
		if(!field.getText().isEmpty()){
			try {
				Partie partie = new Partie(field.getText());
				main.initGame(partie);
			}catch(Exception e) {
				System.err.println("La partie n'a pas pu être enregistrée.");
			}
		}
	}

	/**
	 * <h1>setMainApp</h1>
	 * <p>Sets the object main and initialize the data in the widgets.</p>
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;
		try {
			image.setImage(new Image(new FileInputStream(new File("images/Logo.png"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
