package com.martin.model;

/**
 * <h1>class Coordonnées</h1>
 * <p>
 * Object who defines x and y coordinates.<br/>
 * Mainly used by devices.
 * </p>
 *
 */
public class Coordonnees {

	private Long idCoordonnees;

	private int x, y;

	/**
	 * <h1>constructor Coordonnées</h1>
	 * <p>
	 * Creates coordinates.
	 * </p>
	 * 
	 * @param x x coordinates of the object
	 * @param y y coordinates of the object
	 */
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordonnees() {
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
		if (x < 0 || y < 0 || x > tailleMax || y > tailleMax) {
			return false;
		}
		return true;
	}

	/**
	 * This methods checks if the given coordinates is near from this
	 * object. Two coordinates are 'near' when at least one side in
	 * common.
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if the coordinate is near from the other
	 *         <li>false if the coordinate isn't near from the other
	 *         </ul>
	 * @param coordonnées a coordinate object to compare
	 */
	public boolean isNearFrom(Coordonnees coordonnées) {
		if (this.x == coordonnées.getX() + 1
				|| this.x == coordonnées.getX() - 1) {
			return true;
		}
		if (this.y == coordonnées.getY() + 1
				|| this.x == coordonnées.getY() - 1) {
			return true;
		}
		return false;
	}
}
