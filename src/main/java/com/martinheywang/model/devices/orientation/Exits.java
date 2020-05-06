package com.martinheywang.model.devices.orientation;

import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.devices.Direction;

public class Exits {

	/**
	 * The type Sorties cannot be instantiated.
	 */
	private Exits() {
	}

	/**
	 * Returns a List of Direction. In this case, there are no exit, so
	 * this List contains one element : Direction.NONE
	 * 
	 * @return the list of all possible exits
	 */
	public static List<Direction> listForNone() {
		return Arrays.asList(Direction.NONE);
	}

	/**
	 * Returns a List of Direction. In this case, the only exit is found
	 * on the top-side of the device (assuming that his rotation equals to
	 * 0.0).
	 * 
	 * @param direction the current rotation of the device
	 * @return the list of all possible exits
	 */
	public static List<Direction> listForUp(Direction direction) {

		switch (direction) {
		case UP:
			return Arrays.asList(Direction.UP);
		case RIGHT:
			return Arrays.asList(Direction.LEFT);
		case DOWN:
			return Arrays.asList(Direction.DOWN);
		case LEFT:
			return Arrays.asList(Direction.RIGHT);
		default:
			return Arrays.asList(Direction.NONE);
		}
	}

	/**
	 * Returns a List of Direction. In this case, the only exit is found
	 * on the right-side of the device (assuming that his rotation equals
	 * to 0.0).
	 * 
	 * @param direction the current rotation of the device
	 * @return the list of all possible exits
	 */
	public static List<Direction> listForRight(Direction direction) {

		switch (direction) {
		case UP:
			return Arrays.asList(Direction.LEFT);
		case RIGHT:
			return Arrays.asList(Direction.DOWN);
		case DOWN:
			return Arrays.asList(Direction.RIGHT);
		case LEFT:
			return Arrays.asList(Direction.UP);
		default:
			return Arrays.asList(Direction.NONE);
		}
	}

	/**
	 * Returns a List of Direction. In this case, the only exit is found
	 * on the bottom-side of the device (assuming that his rotation equals
	 * to 0.0).
	 * 
	 * @param direction the current rotation of the device
	 * @return the list of all possible exits
	 */
	public static List<Direction> listForCenter(Direction direction) {

		switch (direction) {
		case UP:
			return Arrays.asList(Direction.DOWN);
		case RIGHT:
			return Arrays.asList(Direction.RIGHT);
		case DOWN:
			return Arrays.asList(Direction.UP);
		case LEFT:
			return Arrays.asList(Direction.LEFT);
		default:
			return Arrays.asList(Direction.NONE);
		}
	}

	/**
	 * Returns a List of Direction. In this case, the only exit is found
	 * on the left-side of the device (assuming that his rotation equals
	 * to 0.0).
	 * 
	 * @param direction the current rotation of the device
	 * @return the list of all possible exits
	 */
	public static List<Direction> listForLeft(Direction direction) {

		switch (direction) {
		case UP:
			return Arrays.asList(Direction.RIGHT);
		case RIGHT:
			return Arrays.asList(Direction.UP);
		case DOWN:
			return Arrays.asList(Direction.LEFT);
		case LEFT:
			return Arrays.asList(Direction.DOWN);
		default:
			return Arrays.asList(Direction.NONE);
		}
	}

}
