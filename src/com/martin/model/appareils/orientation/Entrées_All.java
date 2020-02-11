package com.martin.model.appareils.orientation;

import java.util.ArrayList;

import com.martin.model.appareils.Direction;

public class Entr�es_All implements Entr�es {

	@Override
	public ArrayList<Direction> getPointers(Direction direction) {

		
		ArrayList<Direction> liste = new ArrayList<Direction>();
		
		switch(direction) {
		case UP:		
			liste.add(Direction.DOWN);
			liste.add(Direction.LEFT);
			liste.add(Direction.RIGHT);
			break;
		case RIGHT:
			liste.add(Direction.RIGHT);
			liste.add(Direction.DOWN);
			liste.add(Direction.UP);
			break;
		case DOWN:
			liste.add(Direction.UP);
			liste.add(Direction.RIGHT);
			liste.add(Direction.LEFT);
			break;
		case LEFT:
			liste.add(Direction.UP);
			liste.add(Direction.LEFT);
			liste.add(Direction.DOWN);
			break;
		default:
			liste.add(Direction.NONE);
		}
		
		return liste;
	}

}
