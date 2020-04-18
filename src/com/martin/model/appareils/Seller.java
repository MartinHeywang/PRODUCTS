package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Coordinates;
import com.martin.model.Stock;
import com.martin.model.appareils.comportement.Seller_;
import com.martin.model.appareils.orientation.Entrances;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Seller extends Device {

	public Seller(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		for (Direction entrance : Entrances.listForUp(model.getDirection())) {
			exitsPointers
					.add(new Coordinates(model.getCoordinates(), entrance));
		}

		// Todo : test behaviour
		behaviour = new Seller_(model, controller);
	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {
		System.out.println("action(Stock) in type Seller");
		behaviour.action(resATraiter, null);
	}

}