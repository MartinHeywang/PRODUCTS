package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.view.JeuContrôle;

public class Appareil_Convoyeur extends Appareil {

	public Appareil_Convoyeur(AppareilModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		entrances = Entrées.listForUp(model.getDirection());
		exits = Sorties.listForCenter(model.getDirection());

		// Todo : addd behaviour
	}

}
