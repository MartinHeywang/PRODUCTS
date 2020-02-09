
/*
 * Classe permettant la connection à la BdD (Base de donnée) PostgreSQL. 
 */
package com.martin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Connect_PostgreSQL {
	private final static String url = "jdbc:postgresql://localhost:5432/PRODUCTS.";
	private final static String user = "postgres";
	private final static String passwd = "postgres";
	private static Connection connect;
	
	/**
	 * <h1>getInstance</h1>
	 * @return the instance to the database PostgreSQL
	 */
	public static Connection getInstance() {
		if(connect == null) {
			try {
				connect = DriverManager.getConnection(url, user, passwd);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur de connexion à PostgreSQL", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		return connect;
	}
}
