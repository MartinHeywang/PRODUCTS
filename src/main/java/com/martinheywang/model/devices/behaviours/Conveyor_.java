package com.martinheywang.model.devices.behaviours;

import java.math.BigInteger;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Conveyor_ extends Behaviour {

	private Level level;
	private GameController controller;

	public Conveyor_(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resATraiter, Coordinate pointer)
			throws MoneyException {
		for (int i = 0; i < this.level.getValue()
				|| i < resATraiter.getQuantity(); i++) {
			if (controller.getMoney()
					.compareTo(BigInteger
							.valueOf(5 + Device.getElectricity())) == -1)
				throw new MoneyException();

			controller.removeMoney(BigInteger.valueOf(Device.getElectricity()));
		}
		controller.findDevice(pointer).action(resATraiter);
	}

}
