package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Packing;
import com.martinheywang.model.exceptions.MoneyException;

public class None_ implements Behaviour {
	@Override
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {
		/*
		 * This method does actually nothing but is necessary for devices like
		 * the floor. They do nothing.
		 */

	}

}