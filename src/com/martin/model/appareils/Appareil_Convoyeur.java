package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonn�es;
import com.martin.model.appareils.comportement.Comportement_Convoyeur;
import com.martin.model.appareils.orientation.Entr�es_Center;
import com.martin.model.appareils.orientation.Sorties_Center;
import com.martin.view.JeuContr�le;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Convoyeur extends Appareil {
	
	static SimpleIntegerProperty prix;
	
	public Appareil_Convoyeur(Coordonn�es xy, Direction direction, NiveauAppareil niveau, JeuContr�le controller) 
			throws FileNotFoundException{
		super(xy, TypeAppareil.CONVOYEUR, direction, niveau, controller);
		
		entr�es = new Entr�es_Center();
		pointerEnter = entr�es.getPointerEnter(direction);
		sorties = new Sorties_Center();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Convoyeur(xy, niveau, pointerExit.getxPlus(), 
				pointerExit.getyPlus(), controller);
	}
	public static void initializeData() {
		try {
			prix = new SimpleIntegerProperty(Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixConvoyeur"));
		} catch (SQLException e) {
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE R�CUP�RATION DES DONN�ES du "
					+ "prix du convoyeur"+e.getMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}
	
}
