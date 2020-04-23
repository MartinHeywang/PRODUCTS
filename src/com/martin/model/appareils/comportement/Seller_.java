package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
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
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {
		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.getQuantity(); i++) {
			if (!resATraiter.getRessource().equals(Resource.NONE))
				controller.setArgent(
						resATraiter.getRessource().getValue() - Device
								.getElectricity(),
						true);
		}
	}

}