package com.martin.model.appareils.comportement;

import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Presse implements Comportement {

	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	public Comportement_Presse(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller){
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
	}
	
	@Override
	public void action(Ressource[] resATraiter) throws NegativeArgentException {
		Ressource[] toSend = new Ressource[niveau.getNiveau()];
		
		for(int i = 0; i < this.niveau.getNiveau(); i++) {
			switch(resATraiter[i]) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				toSend[i] = Ressource.valueOf("PLAQUE_DE_"+resATraiter[i].toString());
				
				controller.setArgent(Appareil.getÉlectricité(), false);
			default:
				break;
			}
		}
		controller.getGrilleAppareils(pointer).action(toSend);

	}

}
