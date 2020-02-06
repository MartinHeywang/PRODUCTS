package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public class Entrées_Right implements Entrées {

	@Override
	public Direction getPointerEnter(Direction direction) {
		switch(direction) {
		case UP:		
			return Direction.LEFT;
		case RIGHT:
			return Direction.DOWN;
		case DOWN:
			return Direction.RIGHT;
		case LEFT:
			return Direction.UP;
		default:
			return direction;
		}
	}

}
