package com.martin.model.appareils.comportement;

import com.martin.model.Coordonn�es;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContr�le;

public class Comportement_Presse_Fil implements Comportement {

	private Coordonn�es pointer;
	private NiveauAppareil niveau;
	private JeuContr�le controller;

	public Comportement_Presse_Fil(Coordonn�es xy, NiveauAppareil niveau,
			int xToAdd, int yToAdd, JeuContr�le controller) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonn�es(xy.getX() + xToAdd, xy.getY() + yToAdd);
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {
		final Stock tempoStock = new Stock();

		for (int i = 0; i < this.niveau.getNiveau(); i++) {
			switch (resATraiter.get(i).getRessource()) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				tempoStock.add(new Paquet(Ressource.valueOf(
						"FIL_DE_" + resATraiter.get(i).getRessource()),
						resATraiter.get(i).getQuantit�()));

				controller.setArgent(Appareil.get�lectricit�(), false);
				break;
			default:
				break;
			}
		}
		controller.getGrilleAppareils(pointer).action(tempoStock);

	}

}
