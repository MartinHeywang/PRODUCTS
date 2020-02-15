package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public class Sorties_Up implements Sorties {

	@Override
	public Direction getPointer(Direction direction) {
		switch(direction) {
		case UP:		
			return Direction.UP;
		case RIGHT:
			return Direction.LEFT;
		case DOWN:
			return Direction.DOWN;
		case LEFT:
			return Direction.RIGHT;
		default:
			return Direction.NONE;
		}
	}

}
