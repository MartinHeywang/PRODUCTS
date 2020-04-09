package com.martin.model.appareils.comportement;

import com.martin.model.Coordonnees;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Niveau;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Vendeur implements Comportement {

	private Niveau niveau;
	private JeuContrôle controller;

	public Comportement_Vendeur(Coordonnees xy, Niveau niveau,
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.niveau = niveau;
		this.controller = controller;
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {
		for (int i = 0; i < this.niveau.getNiveau()
				|| i < resATraiter.size(); i++) {
			if (!resATraiter.get(i).getRessource().equals(Ressource.NONE))
				controller.setArgent(
						resATraiter.get(i).getRessource().getValue() - Appareil
								.getÉlectricité(),
						true);
		}
	}

}
