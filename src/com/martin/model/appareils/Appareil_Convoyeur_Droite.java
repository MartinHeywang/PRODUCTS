package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.view.JeuContrôle;

public class Appareil_Convoyeur_Droite extends Appareil {

	public Appareil_Convoyeur_Droite(AppareilModel model,
			JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		entrances = Entrées.listForUp(model.getDirection());
		exits = Sorties.listForRight(model.getDirection());

		// Todo: add behaviour
	}
}
