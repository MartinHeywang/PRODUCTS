package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public class Entrées_Aucune implements Entrées {

	@Override
	public Direction getPointerEnter(Direction direction) {
		/*Ici, inutile de chercher une entrée, puisque de toute façon l'appareil n'aura aucune 
		 * entrées*/
		return Direction.NONE;
	}

}
