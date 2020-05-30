package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Seller_ implements Behaviour {

	private Level level;
	private GameController controller;

	public Seller_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;
	}

	@Override
	public void action(Pack resATraiter, Coordinates pointer)
			throws MoneyException {
		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.getQuantity(); i++) {
			if (!resATraiter.getRessource().equals(BaseResources.NONE))
				controller.setArgent(
						resATraiter.getRessource().getPrice() - Device
								.getElectricity(),
						true);
		}
	}

}