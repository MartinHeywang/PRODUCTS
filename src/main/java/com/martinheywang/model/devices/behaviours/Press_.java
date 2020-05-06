package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Packing;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Press_ implements Behaviour {

	private Level level;
	private GameController controller;

	public Press_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;
	}

	@Override
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {

		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.getQuantity(); i++) {
			switch (resATraiter.getRessource()) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				final Packing tempo = new Packing();
				tempo.addQuantity(1);
				tempo.setRessource(Resource
						.valueOf("PLAQUE_DE_" + resATraiter.getRessource()));

				controller.setArgent(Device.getElectricity(), false);
				controller.findDevice(pointer).action(tempo);
				break;
			default:
				break;
			}
		}

	}

}