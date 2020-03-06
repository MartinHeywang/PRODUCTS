package com.martin;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.martin.model.Coordonn�es;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Stock;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Connect_SQLite {
	private static final String DATABASE_URL = "jdbc:sqlite:PRODUCTS.db";
	private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
	private static ConnectionSource connect;
	
	private static void createConnection() {
		try {
			Class.forName(DATABASE_DRIVER);
			DriverManager.getConnection(DATABASE_URL);
			connect = new JdbcConnectionSource(DATABASE_URL);
			
			//TableUtils.dropTable(connect, Appareil.class, true);
			//TableUtils.dropTable(connect, Coordonn�es.class, true);
			//TableUtils.dropTable(connect, Partie.class, true);
			
			TableUtils.createTableIfNotExists(connect, Appareil.class);
			TableUtils.createTableIfNotExists(connect, Coordonn�es.class);
			TableUtils.createTableIfNotExists(connect, Partie.class);
			TableUtils.createTableIfNotExists(connect, Stock.class);
			
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			Alert alerte = new Alert(AlertType.ERROR);
			alerte.setHeaderText("La connexion � la base de donn�es a �chou�");
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
			e.printStackTrace();
			return null;
		}
		
	}
	public static Dao<Coordonn�es, Integer> getCoordonn�esDao() throws NullPointerException{
		if(connect == null) {
			createConnection();
		}
		Dao<Coordonn�es, Integer> dao;
		try {
			dao = DaoManager.createDao(connect, Coordonn�es.class);
			return dao;
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Appareil getAppareil(Coordonn�es xy, Partie partie) throws NullPointerException {
		try {
			return Connect_SQLite.getAppareilDao().queryBuilder().where()
					.eq("partie_idPartie", partie.getID()).and()
					.eq("xy_idCoordonn�es", xy.getID())
					.queryForFirst().toInstance();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Dao<Stock, Integer> getStockDao() {
		if(connect == null) {
			createConnection();
		}
		Dao<Stock, Integer> dao;
		try {
			dao = DaoManager.createDao(connect, Stock.class);
			return dao;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}