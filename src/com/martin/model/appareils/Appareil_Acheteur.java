package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement_Acheteur;
import com.martin.model.appareils.orientation.Entrées_Aucune;
import com.martin.model.appareils.orientation.Sorties_Center;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Acheteur extends Appareil {
	
	//La ressource distribuée par l'acheteur
	Ressource resDistribuée = Ressource.NONE;
	
	private static SimpleIntegerProperty prix;
	public static ArrayList<Coordonnées> liste = new ArrayList<Coordonnées>();
	
	public Appareil_Acheteur(Coordonnées xy, NiveauAppareil niveau, Direction direction, 
			JeuContrôle controller) throws FileNotFoundException {
		
		super(xy, TypeAppareil.ACHETEUR, direction, niveau, controller);
		
		ressources = new ArrayList<Ressource>();
		prix = new SimpleIntegerProperty(500);
		liste.add(xy);
		
		entrées = new Entrées_Aucune();
		pointerEnter = Direction.NONE;
		sorties = new Sorties_Center();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Acheteur(xy, niveau, pointerExit.getxPlus(), 
				pointerExit.getyPlus(), controller);
	}
	
	@Override 
	public void destruction() {
		try {
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE appareils_infos SET '"+(xy.getX()+1)+"' = 'NONE' WHERE id = '"+(
							xy.getY()+1)+"';");
		} catch (Exception e) {
			System.out.println("ERREUR dans Appareil_Acheteur dans la méthode " + "destruction. Raison :\n"
					+ e.getLocalizedMessage());
		}
	}
	//Retourne la ressource distribuée
	public Ressource getRessourceDistribuée() {
		return resDistribuée;
	}
	//Set la ressource distribuée dans la base de donnée et dans la variable locale
	public void setRessourceDistribuée(Ressource res) throws SQLException{
		
		this.resDistribuée = res;
		
		
		Connect_SQLite.getInstance().createStatement().executeUpdate(
				"UPDATE appareils_infos SET '"+(xy.getX()+1)+"' = \""+res.toString()+"\" "
						+ "WHERE id = "+(xy.getY()+1)+";");
	}
	
	public static void initializeData() {
		try {
			prix = new SimpleIntegerProperty(Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixAcheteur"));
		} catch (SQLException e) {
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE RÉCUPÉRATION DES DONNÉES du "
					+ "prix de l'acheteur"+e.getMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}
}