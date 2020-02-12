package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement_Assembleur;
import com.martin.model.appareils.orientation.Entrées_LeftAndCenter;
import com.martin.model.appareils.orientation.Sorties_Right;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Assembleur extends Appareil {

	Ressource produit = Ressource.NONE;
	
	ArrayList<Ressource> recette = new ArrayList<Ressource>();
	
	private static SimpleIntegerProperty prix;
	
	public Appareil_Assembleur(Coordonnées xy, NiveauAppareil niveau, Direction direction, JeuContrôle controller) throws FileNotFoundException {
		super(xy, TypeAppareil.ASSEMBLEUR, direction, niveau, controller);
		
		entrées = new Entrées_LeftAndCenter();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Right();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Assembleur(xy, niveau, pointerExit.getxPlus(), 
				pointerExit.getyPlus(), controller);
	}
	public void setProduit(Ressource res) {
		this.produit = res;
		
		((Comportement_Assembleur) comportement).setProduit(produit);
		
		try {
			Connect_SQLite.getInstance().createStatement().executeUpdate("UPDATE appareils_infos "
					+ "SET '"+(xy.getX()+1)+"' = \""+res.toString()+"\" WHERE id = "+(xy.getY()+1)+";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void initializeData() {
		try {
			prix = new SimpleIntegerProperty(Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixAssembleur"));
		} catch (SQLException e) {
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE RÉCUPÉRATION DES DONNÉES du "
					+ "prix de l'assembleur"+e.getMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}
}
