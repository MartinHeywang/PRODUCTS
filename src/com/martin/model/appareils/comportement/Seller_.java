package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Resource;
import com.martin.model.Stock;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.DeviceModel;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Seller_ implements Behaviour {

	private Level level;
	private JeuContrôle controller;

	public Seller_(DeviceModel model, JeuContrôle controller) {
		this.level = model.getNiveau();
		this.controller = controller;
	}

	@Override
	public void action(Stock resATraiter, Coordinates pointer)
			throws MoneyException {
		System.out.println("The behaviour of a seeler has begun");
		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.size(); i++) {
			if (!resATraiter.get(i).getRessource().equals(Resource.NONE))
				controller.setArgent(
						resATraiter.get(i).getRessource().getValue() - Device
								.getElectricity(),
						true);
		}
	}

}