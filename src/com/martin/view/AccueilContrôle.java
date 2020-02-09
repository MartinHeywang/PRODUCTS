package com.martin.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;

import com.martin.Connect_SQLite;
import com.martin.Main;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AccueilContrôle {
	
	@FXML TextField field;
	@FXML ImageView image;
	
	Main main;
	
	/**
	 * <h1>initialize</h1>
	 * <p>Initialize the start stage, who asks for a login. Called automatically as a constructor.</p>
	 */
	public void initialize() {
		try {
			image.setImage(new Image(new FileInputStream(new File("images/Logo.png"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <h1>seConnecter</h1>
	 * <p>Launches the real game and registers the login in the database</p>	 
	 * */
	@FXML
	public void seConnecter() {
		if(!field.getText().isEmpty()){
			try {
				Statement state = Connect_SQLite.getInstance().createStatement();
				state.executeUpdate("UPDATE infos SET pseudo = '"+field.getText()+"';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			main.initGame();
		}
	}

	/**
	 * <h1>setMainApp</h1>
	 * <p>Sets the object main.</p>
	 * @param main the object to set
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	
}
