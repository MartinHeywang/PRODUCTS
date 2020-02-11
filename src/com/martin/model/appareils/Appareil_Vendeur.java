package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.appareils.comportement.Comportement_Vendeur;
import com.martin.model.appareils.orientation.Entrées_Center;
import com.martin.model.appareils.orientation.Sorties_Aucune;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Vendeur extends Appareil {

	private static SimpleIntegerProperty prix;
	
	public Appareil_Vendeur(Coordonnées xy, Direction direction, NiveauAppareil niveau,  
			JeuContrôle controller) throws FileNotFoundException {
		super(xy, TypeAppareil.VENDEUR, direction, niveau, controller);
		
		prix = new SimpleIntegerProperty(500);
		
		entrées = new Entrées_Center();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Aucune();
		pointerExit = sorties.getPointer(direction);
		
		comportement = new Comportement_Vendeur(xy, niveau, direction.getxPlus(), direction.getyPlus(), controller);
	}
	public static void initializeData() {
		try {
			prix = new SimpleIntegerProperty(Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixVendeur"));
		} catch (SQLException e) {
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE RÉCUPÉRATION DES DONNÉES du "
					+ "prix du vendeur"+e.getLocalizedMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}

	@Override
	public void destruction() {}
	
}