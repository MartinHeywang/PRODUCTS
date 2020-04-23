package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.exceptions.MoneyException;

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
		 * Cette m�thode ne d�finit aucun comportement mais est n�cessaire
		 * pour les appareils comme les sols.
		 */

	}

}
