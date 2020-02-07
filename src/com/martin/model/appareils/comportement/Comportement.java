package com.martin.model.appareils.comportement;

import com.martin.model.Ressource;
import com.martin.model.exceptions.NegativeArgentException;

public interface Comportement {
	public void action(Ressource resATraiter) throws NegativeArgentException;
}
