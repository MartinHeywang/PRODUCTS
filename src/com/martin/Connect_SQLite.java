package com.martin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Connect_SQLite {
	private static final String url = "jdbc:sqlite:Data_File.db";
	private static Connection connect;
	
	public static Connection getInstance() {
		if(connect == null) {
			try {
				connect = DriverManager.getConnection(url);
			} catch (SQLException e) {
				Alert alerte = new Alert(AlertType.ERROR);
				alerte.setHeaderText("La connexion à la base de données a échoué");
				alerte.setContentText("Raison : "+e.getMessage());
				System.exit(0);
			}
		}
		return connect;
	}
}