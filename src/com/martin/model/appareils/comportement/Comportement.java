package com.martin.model.appareils.comportement;

import com.martin.model.Ressource;
import com.martin.model.exceptions.NegativeArgentException;

public interface Comportement {
	/**
	 * <h1>action</h1>
	 * <p>This method introduces devices behaviour.</p>
	 * 
	 * @param resATraiter the resource to use by this device
	 * @throws NegativeArgentException if money reaches 0 or less
	 */
	public void action(Ressource[] resATraiter) throws NegativeArgentException;
}
