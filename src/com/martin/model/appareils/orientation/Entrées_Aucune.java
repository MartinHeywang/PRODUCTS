package com.martin.model.appareils.orientation;

import java.util.ArrayList;

import com.martin.model.appareils.Direction;

public class Entrées_Aucune implements Entrées {

	@Override
	public ArrayList<Direction> getPointers(Direction direction) {
		/*Ici, inutile de chercher une entrée, puisque de toute façon l'appareil n'aura aucune 
		 * entrées*/
		ArrayList<Direction> liste = new ArrayList<Direction>();
		liste.add(Direction.NONE);
		return  liste;
	}

}
