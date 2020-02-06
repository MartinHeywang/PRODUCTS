package com.martin;

import java.sql.SQLException;

public class Stats {
	
	public static final int multiplicateur = 2;
	
	public static int prixAcheteur = 500;
	public static int prixVendeur = 500;
	public static int prixConvoyeur = 1000;
	public static int prixFour = 2000;
	public static int prixPresse = 2000;
	public static int prixPresseFil = 3000;
	public static int prixAssembleur = 7_500;
	public static int prixTrieur = 10_000;
	
	
	public static int maxAcheteur = 4, nbreAcheteur = 0;
	public static int schéma = 2;
	public static int niveau = 1;
	
	public static int prixRechercheElectricité = 150_000;
	public static int prixRechercheMaxAcheteur = 150_000;
	public static int prixRechercheSchéma = 50_000;
	public static int prixRechercheNiveau = 1_000_000;
	
	public static int prixAgrandissement = 20_000;
	
	public static void initialize() {
		// REMPLACER les lectures dans les descritpions d'appareils

		try {
			prixAcheteur  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixAcheteur");
			prixAssembleur  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixAssembleur");
			prixConvoyeur  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixConvoyeur");
			prixFour  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixFour");
			prixPresse  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixPresse");
			prixPresseFil  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixPresseFil");
			prixTrieur  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixTrieur");
			prixVendeur  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixVendeur");
			maxAcheteur  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("maxAcheteur");
			schéma  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("schéma");
			niveau  = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("niveau");
			
			prixAgrandissement = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixAgrandissement");
		} catch (SQLException e) {
			System.out.println("AVERTISSEMENT : Erreur lors du chargement des données. Raison :\n"+e.getMessage());
		}
	}
	public static void sauvegarder() throws SQLException{
		// REMPLACER les sauvegardes dans les descriptions des appareils.
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixAcheteur = "+Stats.prixAcheteur+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixAssembleur = "+Stats.prixAssembleur+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixConvoyeur = "+Stats.prixConvoyeur+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixFour = "+Stats.prixFour+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixPresse = "+Stats.prixPresse+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixPresseFil = "+Stats.prixPresseFil+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixTrieur = "+Stats.prixTrieur+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixVendeur = "+Stats.prixVendeur+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET maxAcheteur = "+Stats.maxAcheteur+";");
			
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET schéma = "+Stats.schéma+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET niveau = "+Stats.niveau+";");
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE infos SET prixAgrandissement = "+Stats.prixAgrandissement+";");
	}
}
