package com.martin.model.appareils.orientation;

import java.util.ArrayList;

import com.martin.model.appareils.Direction;

public class Entrées_Left implements Entrées {

	@Override
	public ArrayList<Direction> getPointers(Direction direction) {
		
		ArrayList<Direction> liste = new ArrayList<Direction>();
		
		switch(direction) {
		case UP:			
			liste.add(Direction.RIGHT);
		case RIGHT:
			liste.add(Direction.UP);
		case DOWN:
			liste.add(Direction.LEFT);
		case LEFT:
			liste.add(Direction.DOWN);
		default:
			liste.add(Direction.NONE);
		}
		
		return liste;
	}

}
