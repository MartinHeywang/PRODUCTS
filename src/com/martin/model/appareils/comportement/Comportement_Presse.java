package com.martin.model.appareils.comportement;

import com.martin.model.Coordonnees;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Presse implements Comportement {

	private Coordonnees pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;

	public Comportement_Presse(Coordonnees xy, NiveauAppareil niveau,
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnees(xy.getX() + xToAdd, xy.getY() + yToAdd);
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
						"PLAQUE_DE_" + resATraiter.get(i).getRessource()),
						resATraiter.get(i).getQuantité()));

				controller.setArgent(Appareil.getÉlectricité(), false);
				break;
			default:
				break;
			}
		}
		controller.getPartieEnCours().getAppareil(pointer).action(tempoStock);

	}

}