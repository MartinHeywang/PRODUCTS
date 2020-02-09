package com.martin.model.appareils.comportement;

import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Vendeur implements Comportement {

	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	public Comportement_Vendeur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller){
		this.niveau = niveau;
		this.controller = controller;
	}
	
	@Override
	public void action(Ressource resATraiter) throws NegativeArgentException{
		for(int i = 0; i < niveau.getNiveau(); i++) {
			if(!resATraiter.equals(Ressource.NONE))
				controller.setArgent(resATraiter.getValue()-Appareil.getÉlectricité(), true);
		}
	}

}
