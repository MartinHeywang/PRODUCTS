package com.martin;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.martin.model.Coordonnées;
import com.martin.model.appareils.Appareil;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Connect_SQLite{
	private static final String DATABASE_URL = "jdbc:sqlite:PRODUCTS.db";
	private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
	private static ConnectionSource connect;
	
	private static void createConnection() {
		try {
			Class.forName(DATABASE_DRIVER);
			DriverManager.getConnection(DATABASE_URL);
			connect = new JdbcConnectionSource(DATABASE_URL);
			
			List<Appareil> appareil = DaoManager.createDao(connect, Appareil.class);
			List<Coordonnées> coordonnées = DaoManager.createDao(connect, Coordonnées.class);
			List<Partie> partie = DaoManager.createDao(connect, Partie.class);
			
			TableUtils.dropTable(connect, Appareil.class, true);
			TableUtils.dropTable(connect, Coordonnées.class, true);
			TableUtils.dropTable(connect, Partie.class, true);
			
			TableUtils.createTableIfNotExists(connect, Appareil.class);
			DaoManager.createDao(connect, Appareil.class).create(appareil);
			TableUtils.createTableIfNotExists(connect, Coordonnées.class);
			DaoManager.createDao(connect, Coordonnées.class).create(coordonnées);
			TableUtils.createTableIfNotExists(connect, Partie.class);
			DaoManager.createDao(connect, Partie.class).create(partie);
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			Alert alerte = new Alert(AlertType.ERROR);
			alerte.setHeaderText("La connexion à la base de données a échoué");
			alerte.setContentText("Raison : "+e.getMessage());
			System.exit(1);
		}
		
	}
	public static ConnectionSource getConnection() {
		if(connect == null) {
			createConnection();
		}
		return connect;
	}
	public static Dao<Appareil, Integer> getAppareilDao() throws NullPointerException{
		if(connect == null) {
			createConnection();
		}
		Dao<Appareil, Integer> dao;
		try {
			dao = DaoManager.createDao(connect, Appareil.class);
			return dao;
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		
	}
	public static Dao<Coordonnées, Integer> getCoordonnéesDao() throws NullPointerException{
		if(connect == null) {
			createConnection();
		}
		Dao<Coordonnées, Integer> dao;
		try {
			dao = DaoManager.createDao(connect, Coordonnées.class);
			return dao;
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		
	}
	public static Dao<Partie, Integer> getPartieDao() throws NullPointerException{
		if(connect == null) {
			createConnection();
		}
		Dao<Partie, Integer> dao;
		try {
			dao = DaoManager.createDao(connect, Partie.class);
			return dao;
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		
	}
}