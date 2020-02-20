package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.martin.Connect_SQLite;
import com.martin.Partie;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement_Acheteur;
import com.martin.model.appareils.orientation.Entrées_Aucune;
import com.martin.model.appareils.orientation.Sorties_Center;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

public class Appareil_Acheteur extends Appareil {
	
	//La ressource distribuée par l'acheteur
	@DatabaseField(columnName = "Stock 1")
	Ressource resDistribuée = Ressource.NONE;
	
	private static SimpleIntegerProperty prix;
	public static ArrayList<Coordonnées> liste = new ArrayList<Coordonnées>();
	
	public Appareil_Acheteur(Coordonnées xy, Direction direction, NiveauAppareil niveau,  
			JeuContrôle controller, Partie partie) throws FileNotFoundException {
		
		super(xy, TypeAppareil.ACHETEUR, direction, niveau, controller, partie);
		liste.add(xy);
		
		entrées = new Entrées_Aucune();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Center();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Acheteur(xy, niveau, pointerExit.getxPlus(), 
				pointerExit.getyPlus(), controller);
	}
	@Override 
	public void destruction() {
		try {
			Connect_SQLite.getAppareilDao().delete(this);
		} catch (Exception e) {
			controller.setReport("L'appareil n'a pas été détruit correctement.", Color.DARKRED);
		}
	}
	//Retourne la ressource distribuée
	public Ressource getRessourceDistribuée() {
		return resDistribuée;
	}
	//Set la ressource distribuée dans la base de donnée et dans la variable locale
	public void setRessourceDistribuée(Ressource res) throws SQLException{
		
		this.resDistribuée = res;
		
		((Appareil_Acheteur) comportement).setRessourceDistribuée(resDistribuée);
		Connect_SQLite.getAppareilDao().update(this);
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