package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public class Entrées_Left implements Entrées {

	@Override
	public Direction getPointerEnter(Direction direction) {
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
