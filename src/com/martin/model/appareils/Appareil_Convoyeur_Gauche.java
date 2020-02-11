package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Coordonnées;
import com.martin.model.appareils.comportement.Comportement_Convoyeur;
import com.martin.model.appareils.orientation.Entrées_Center;
import com.martin.model.appareils.orientation.Sorties_Left;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Convoyeur_Gauche extends Appareil {
	
private static SimpleIntegerProperty prix;
	
	public Appareil_Convoyeur_Gauche(Coordonnées xy, Direction direction, NiveauAppareil niveau, JeuContrôle controller) 
			throws FileNotFoundException{
		super(xy, TypeAppareil.CONVOYEUR_GAUCHE, direction, niveau, controller);
		
		entrées = new Entrées_Center();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Left();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Convoyeur(xy, niveau, pointerExit.getxPlus(), 
				pointerExit.getyPlus(), controller);
	}
	public static void initializeData() {
		prix = new SimpleIntegerProperty();
		prix.bind(Appareil_Convoyeur.prix);
	}
	public static int getPrix() {
		return prix.get();
	}
	
}
