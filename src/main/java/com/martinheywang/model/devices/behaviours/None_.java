package com.martinheywang.model.devices.behaviours;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Packing;
import com.martinheywang.model.exceptions.MoneyException;

public class None_ implements Behaviour {

	/**
	 * <b>action</b>
	 * <p>
	 * R�alise l'action de l'appareil
	 * </p>
	 * 
	 * @param resATraiter la ressource � traiter par l'appareil
	 * 
	 */
	@Override
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {
		/*
		 * Cette méthode ne définit aucun comportement mais est n�cessaire
		 * pour les appareils comme les sols.
		 */

	}

}
