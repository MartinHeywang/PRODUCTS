package com.martin.model.appareils.comportement;

import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Presse_Fil implements Comportement {

	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	public Comportement_Presse_Fil(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller){
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
	}
	
	@Override
	public void action(Ressource resATraiter) throws NegativeArgentException {
		for(int i = 0; i < this.niveau.getNiveau(); i++) {
			switch(resATraiter) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				Ressource resTransformée;
				resTransformée = Ressource.valueOf("FIL_DE_"+resATraiter.toString());
				controller.getGrilleAppareils(pointer).action(resTransformée);
				controller.setArgent(Appareil.getÉlectricité(), false);
			default:
				break;
			}
		}

	}

}
