package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public class Sorties_Center implements Sorties {

	@Override
	public Direction getPointer(Direction direction) {
		
		switch(direction) {
		case UP:		
			return Direction.DOWN;
		case RIGHT:
			return Direction.RIGHT;
		case DOWN:
			return Direction.UP;
		case LEFT:
			return Direction.LEFT;
		default:
			return Direction.NONE;
		}
	}

}
