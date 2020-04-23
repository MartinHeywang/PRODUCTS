package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.exceptions.MoneyException;

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
