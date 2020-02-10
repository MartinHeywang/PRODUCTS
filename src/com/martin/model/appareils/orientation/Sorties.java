package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public interface Sorties {
	/**
	 * <h1>getPointer</h1>
	 * <p>This methode gives an object Direction who corresponds to the pointer of an device.</p>
	 * 
	 * @param direction the rotation of the device
	 * @return the direction of the pointer.
	 */
	public Direction getPointer(Direction direction);
}
