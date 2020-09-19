package com.martinheywang.products.model.direction;

public enum Direction {
	UP(0d, "0°"),
	LEFT(90d, "90°"),
	DOWN(180d, "180°"),
	RIGHT(270d, "-90°"),

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

	public String getLiteral() {
		return literal;
	}
}
