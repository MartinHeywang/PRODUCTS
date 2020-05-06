package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Packing;
import com.martinheywang.model.exceptions.MoneyException;

public interface Behaviour {
	/**
	 * <h1>action</h1>
	 * <p>
	 * This method introduces devices behaviour.
	 * </p>
	 * 
	 * @param resATraiter the resource to use by this device
	 * @throws MoneyException if money reaches 0 or less
	 */
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException;
}
