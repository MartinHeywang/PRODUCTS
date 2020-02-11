package com.martin.model.appareils.orientation;

import java.util.ArrayList;

import com.martin.model.appareils.Direction;

public interface Entrées {
	/**
	 * <h1>getPointers</h1>
	 * <p>This method returns an ArrayList who contains all of the possible pointer of enter, so which 
	 * devices can send resources to its.</p>
	 * 
	 * @param direction the rotation of the device
	 * @return the direction of the pointers
	 */
	public ArrayList<Direction> getPointers(Direction direction);
}
