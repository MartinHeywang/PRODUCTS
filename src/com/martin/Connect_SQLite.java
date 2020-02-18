package com.martin;

import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.martin.model.Coordonnées;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Acheteur;
import com.martin.model.appareils.Appareil_Vendeur;
import com.martin.model.appareils.Direction;
import com.martin.model.appareils.NiveauAppareil;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Connect_SQLite {
	private static final String DATABASE_URL = "jdbc:sqlite:PRODUCTS.db";
	private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
	private static ConnectionSource connect;
	
	/**
	 * <h1>getInstance</h1>
	 * @return the instance to the SQLite database
	 */
	public static void createConnection() {
		try {
			Class.forName(DATABASE_DRIVER);
			DriverManager.getConnection(DATABASE_URL);
			connect = new JdbcConnectionSource(DATABASE_URL);
			
			TableUtils.createTableIfNotExists(connect, Appareil.class);
			TableUtils.createTableIfNotExists(connect, Coordonnées.class);
			
			Dao<Appareil, Integer> dao = DaoManager.createDao(connect, Appareil.class);
			dao.createOrUpdate(new Appareil_Vendeur(new Coordonnées(0, 0), Direction.UP, NiveauAppareil.NIVEAU_2, null));
			dao.createOrUpdate(new Appareil_Vendeur(new Coordonnées(0, 0), Direction.UP, NiveauAppareil.NIVEAU_2, null));
			dao.createOrUpdate(new Appareil_Acheteur(new Coordonnées(20, 75), Direction.DOWN, NiveauAppareil.NIVEAU_3, null));
		} catch (SQLException | ClassNotFoundException e) {
			Alert alerte = new Alert(AlertType.ERROR);
			alerte.setHeaderText("La connexion à la base de données a échoué");
			alerte.setContentText("Raison : "+e.getMessage());
			System.exit(0);
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			
		}
		
	}
	public static ConnectionSource getConnection() {
		if(connect == null) {
			createConnection();
		}
		return connect;
	}
	public static Dao<Appareil, Integer> getAppareilDao() throws NullPointerException{
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
		Dao<Coordonnées, Integer> dao;
		try {
			dao = DaoManager.createDao(connect, Coordonnées.class);
			return dao;
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
		
	}
}