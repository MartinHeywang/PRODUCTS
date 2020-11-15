/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.model.direction;

/**
 * The direction defines the rotation of a device. 
 * As the device view is a square, four rotation positions are available.
 */
public enum Direction {
	/**
	 * No rotation
	 */
	UP(0d, "0°"),

	/**
	 * Rotated 90 degrees.
	 */
	LEFT(90d, "90°"),

	/**
	 * Upside-down, rotated 180 degrees.
	 */
	DOWN(180d, "180°"),

	/**
	 * Rotated -90 (or 270) degrees.
	 */
	RIGHT(270d, "-90°"),

	/**
	 * Unknown value.
	 */
	NONE(0d, "...");

	private double rotate;
	private String literal;

	Direction(double rotate, String literal) {
		this.rotate = rotate;
		this.literal = literal;
	}

	/**
	 * Returns a const of the enum <code>Direction</code>, that is exactly
	 * the nearest neighbors (e.g. {@link #UP} becomes {@link #LEFT}).
	 * Can't return {@link #NONE}, it jumps this step go directly to the
	 * top, as {@link #UP}.
	 * 
	 * @return a direction : the next value in the enum
	 */
	public Direction getNext() {
		// Get the next const enum
		Direction dir = Direction.values()[this.ordinal() + 1];
		// If the direction equals to NONE, we return to UP.
		if (dir.equals(Direction.NONE))
			dir = Direction.UP;
		// Just return it
		return dir;
	}

	/**
	 * @return the rotate
	 */
	public double getRotate() {
		return rotate;
	}

	/**
	 * This is not an equivalent to {@link #toString()}
	 * 
	 * @return the literal, a string representation of this object. (displayed in the UI)
	 */
	public String getLiteral() {
		return literal;
	}
}
