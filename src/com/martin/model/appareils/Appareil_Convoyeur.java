package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Coordonnees;
import com.martin.model.appareils.comportement.Comportement_Convoyeur;
import com.martin.model.appareils.orientation.Entrées_Center;
import com.martin.model.appareils.orientation.Sorties_Center;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Convoyeur extends Appareil {

	static SimpleIntegerProperty prix;

	public Appareil_Convoyeur() {
	}

	public Appareil_Convoyeur(Coordonnees xy, Direction direction,
			NiveauAppareil niveau, JeuContrôle controller)
			throws FileNotFoundException {
		super(xy, TypeAppareil.CONVOYEUR, direction, niveau, controller);

		entrées = new Entrées_Center();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Center();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Convoyeur(xy, niveau,
				pointerExit.getxPlus(),
				pointerExit.getyPlus(), controller);
	}

}
