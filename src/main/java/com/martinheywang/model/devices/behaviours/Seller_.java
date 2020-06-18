package com.martinheywang.model.devices.behaviours;

import java.math.BigInteger;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.BaseResources;
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
			if (controller.getMoney()
					.compareTo(BigInteger
							.valueOf(5 + Device.getElectricity())) == -1)
				throw new MoneyException();
			else {
				if (!resATraiter.getRessource().equals(BaseResources.NONE)) {
					controller.addMoney(
							BigInteger.valueOf(
									resATraiter.getRessource().getPrice()
											- Device
													.getElectricity()));
					device.activate();
				}
			}
		}
	}

}