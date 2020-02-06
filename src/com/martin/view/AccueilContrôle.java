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
	
	public void initialize() {
		try {
			image.setImage(new Image(new FileInputStream(new File("images/Logo.png"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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

	public void setMainApp(Main main) {
		this.main = main;
	}
	
	
}
