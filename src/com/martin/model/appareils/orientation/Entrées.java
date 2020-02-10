package com.martin.model.appareils.orientation;

import com.martin.model.appareils.Direction;

public interface Entrées {
	/**
	 * <h1>getPointerEnter</h1>
	 * <p>This methode gives an object Direction who corresponds to the pointer of an device.</p>
	 * 
	 * @param direction the rotation of the device
	 * @return the direction of the pointer
	 */
	public Direction getPointerEnter(Direction direction);
}
