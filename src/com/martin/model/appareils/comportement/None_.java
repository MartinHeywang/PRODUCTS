package com.martin.model.appareils.comportement;

import com.martin.model.Stock;
import com.martin.model.exceptions.MoneyException;

public class None_ implements Behaviour {

	/**
	 * <b>action</b>
	 * <p>
	 * Réalise l'action de l'appareil
	 * </p>
	 * 
	 * @param resATraiter la ressource à traiter par l'appareil
	 * 
	 */
	@Override
	public void action(Stock resATraiter) throws MoneyException {
		/*
		 * Cette méthode ne définit aucun comportement mais est nécessaire
		 * pour les appareils comme les sols.
		 */

	}

}
