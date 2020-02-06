package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public class Sorties_Left implements Sorties {

	@Override
	public Direction getPointer(Direction direction) {
		
		switch(direction) {
		case UP:			
			return Direction.RIGHT;
		case RIGHT:
			return Direction.UP;
		case DOWN:
			return Direction.LEFT;
		case LEFT:
			return Direction.DOWN;
		default:
			return direction;
		}
	}

}
