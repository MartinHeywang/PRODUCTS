package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class None_ extends Behaviour {

	public None_(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resATraiter, Coordinates pointer)
			throws MoneyException {
		/*
		 * <!> This method does actually nothing but is necessary for devices
		 * like the floor. It does nothing.
		 */

	}

}
