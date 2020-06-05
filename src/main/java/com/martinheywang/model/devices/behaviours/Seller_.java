package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Seller_ extends Behaviour {

	public Seller_(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resATraiter, Coordinates pointer)
			throws MoneyException {
		for (int i = 0; i < this.level.getValue()
				|| i < resATraiter.getQuantity(); i++) {
			if (!resATraiter.getRessource().equals(BaseResources.NONE)) {
				controller.addMoney(
						resATraiter.getRessource().getPrice() - Device
								.getElectricity());
				device.activate();
			}
		}
	}

}