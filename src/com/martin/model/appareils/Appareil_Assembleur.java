package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement_Assembleur;
import com.martin.model.appareils.orientation.Entrées_LeftAndCenter;
import com.martin.model.appareils.orientation.Sorties_Right;
import com.martin.view.JeuContrôle;

public class Appareil_Assembleur extends Appareil {

	ArrayList<Ressource> recette = new ArrayList<Ressource>();

	public Appareil_Assembleur(Coordonnées xy, NiveauAppareil niveau,
			Direction direction, JeuContrôle controller)
			throws FileNotFoundException {
		super(xy, TypeAppareil.ASSEMBLEUR, direction, niveau, controller);

		entrées = new Entrées_LeftAndCenter();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Right();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Assembleur(xy, niveau,
				pointerExit.getxPlus(),
				pointerExit.getyPlus(), controller);
	}

	public void setProduit(Ressource res) throws NullPointerException {
		if (comportement instanceof Comportement_Assembleur) {
			((Comportement_Assembleur) comportement).setProduit(res);
		}
	}

	public Ressource getProduit() throws NullPointerException {
		if (comportement instanceof Comportement_Assembleur)
			return ((Comportement_Assembleur) comportement).getProduit()
					.getRessource();
		return null;
	}
}
