package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Stock;
import com.martin.model.appareils.orientation.Entrances;
import com.martin.model.appareils.orientation.Exits;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Seller extends Device {

	public Seller(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		exits = Entrances.listForBottom(model.getDirection());
		entrances = Exits.listForNone();

		// Todo : add behaviour
	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {
		behaviour.action(resATraiter);
	}

}