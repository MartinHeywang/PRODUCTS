package com.martinheywang.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Coordinate implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(columnName = "id", generatedId = true)
	private Long idCoordonnees;

	@DatabaseField(uniqueCombo = true)
	private int x, y;

	/**
	 * Creates a Coordinates object
	 * 
	 * @param x x coordinates of the object
	 * @param y y coordinates of the object
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate() {
	}

	public Long getIdCoordonnees() {
		return idCoordonnees;
	}

	/**
	 * @return the field x of this object
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the field y of this object
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param id the new id
	 */
	public void setIdCoordonnees(Long id) {
		this.idCoordonnees = id;
	}

	/**
	 * 
	 * @param x the new x value
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y the new y value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * This method checks if this <i>coordinnates</i> object is in the
	 * grid, for a given size. Really useful for <i>devices</i> when we
	 * want to know if his pointer is in grid.
	 * 
	 * @param tailleMax the current size of the grid.
	 * @return
	 *         <ul>
	 *         <li>true if the coordinate is in the grid
	 *         <li>false if the coordinate isn't in grid
	 *         </ul>
	 */
	public boolean isInGrid(int tailleMax) {
		if (x < 0 || y < 0 || x > tailleMax - 1 || y > tailleMax - 1) {
			return false;
		}
		return true;
	}

	/**
	 * This methods checks if the given coordinates is near from this
	 * object. Two coordinates are 'near' when they have at least one side
	 * in common.
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if the coordinate is near from the other
	 *         <li>false if the coordinate isn't near from the other
	 *         </ul>
	 * @param coordinates a coordinate object to compare
	 */
	public boolean isNearFrom(Coordinate coordinates) {
		if (this.x == coordinates.getX() + 1
				|| this.x == coordinates.getX() - 1) {
			return true;
		}
		if (this.y == coordinates.getY() + 1
				|| this.x == coordinates.getY() - 1) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}

	public boolean propertiesEquals(Coordinate coordinates) {
		return this.x == coordinates.getX() && this.y == coordinates.getY()
				? true
				: false;
	}
}
