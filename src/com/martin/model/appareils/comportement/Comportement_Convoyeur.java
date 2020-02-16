package com.martin.model.appareils.comportement;

import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Convoyeur implements Comportement {

	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	public Comportement_Convoyeur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
	}

	@Override
	public void action(Ressource[] resATraiter) throws NegativeArgentException {
		for(int niveau = 0; this.niveau.getNiveau() == niveau+1; niveau++) {
			if(controller.getArgentProperty().get() < 5+Appareil.getÉlectricité())
				throw new NegativeArgentException("Le comportement d'un appareil "
						+ "n'a pas pu être réalisé car le solde d'argent n'était pas assez important.");
			controller.getGrilleAppareils(pointer).action(resATraiter);
			controller.setArgent(Appareil.getÉlectricité(), false);
		}
	}

}
