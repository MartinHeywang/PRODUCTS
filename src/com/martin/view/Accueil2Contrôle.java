package com.martin.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Accueil2Contrôle {
	
	@FXML Label label;
	@FXML ImageView image;
	
	Main main;
	
	/**
	 * <h1>initialize</h1>
	 * <p>Iniialize the start page (when a login is registered) and search the login in the database. Called 
	 * automatically as a constructor.</p>
	 */
	public void initialize() {
		try {
			image.setImage(new Image(new FileInputStream(new File("images/Logo.png"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ResultSet res = Connect_SQLite.getInstance().createStatement().executeQuery("SELECT pseudo FROM infos;");
			label.setText("Bienvenue, "+res.getString(1)+" ! Que veux-tu faire ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <h1>charger</h1>
	 * <p>Launches the game.</p>
	 */
	@FXML
	private void charger() {
		main.initGame();
	}
	
	/**
	 * <h1>charger</h1>
	 * <p>Resets the database (except the login) and launches the game.</p>
	 */
	@FXML
	private void nouvelle() {
		
		FXMLLoader loader = new FXMLLoader();	//Permet de charger des fichier .fxml
		Stage progress = new Stage();
		
		loader.setLocation(Main.class.getResource("view/Progression.fxml"));	//Définit l'emplacement où chercher
		
		GridPane conteneurPrincipal;
		try {
			conteneurPrincipal = (GridPane) loader.load();	//Charge le fichier dans notre variable de contenu, comme prévu

			Scene scène = new Scene(conteneurPrincipal);	//Fenêtre dans laquelle s'affiche notre contenu
			
			progress.setScene(scène);
		} catch (IOException e) {
			e.printStackTrace();
		}		
			
		ProgressContrôle controler = loader.getController();	//Le contrôleur du fichier en question
		controler.setMainApp(progress, main);
		
		progress.show();
	}

	/**
	 * <h1>Sets the object main</p>
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
}
