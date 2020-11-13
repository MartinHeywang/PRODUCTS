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
package io.github.martinheywang.products.api.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * <p>
 * A Coordinate is a location / position of a certain device on the grid. It
 * contains x and y values, corresponding respectively to the x and y position.
 * </p>
 * <p>
 * Any x and y value can exist, like -1 as x and -1 as y. This class has got
 * some useful methods, like {@link #isInGrid()}. The name is pretty
 * self-explanatory. More informations on its description.
 * </p>
 * <p>
 * Instances of this class may be persistent, as it is marked with
 * {@link DatabaseTable}
 * </p>
 */
@DatabaseTable
public class Coordinate implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4647555687321610658L;

    /**
     * The grid size is used to know whether this coordinate represents a coordinate
     * that is in grid.
     */
    private static int gridSize = 0;

    /**
     * The id of this coordinate, if its stored in the database. May be null in case
     * it isn't.
     */
    @DatabaseField(columnName = "id", generatedId = true)
    private Long id;

    /**
     * X Value of the coordinate.
     */
    @DatabaseField(uniqueCombo = true)
    private int x;

    /**
     * Y value of the coordinate.
     */
    @DatabaseField(uniqueCombo = true)
    private int y;

    /**
     * Creates an empty coordinate.
     */
    public Coordinate() {
    }

    /**
     * Creates a Coordinates object
     * 
     * @param x x coordinate of the object
     * @param y y coordinate of the object
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the id of this coordinate in the database. May be null if it hasn't
     * got one.
     * 
     * @return id
     */
    public Long getID() {
        return this.id;
    }

    /**
     * @return the x value of thsi coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return the y value of this coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * <p>
     * Checks if this coordinate is in grid, according to the {@link #gridSize}
     * property. This property must so be updated if you want a correct result. By
     * convention, a coordinate *is in grid* if the x and y value are greater or
     * equal to 0, and if the same values are contained for the given grid size. See
     * the example below for more clarity.
     * </p>
     * 
     * <pre>
     * <code>
     * // The below examples assumes that gridSize = 3
     * 
     * new Coordinate(0, 0).isInGrid() -> always true, whether the grid size
     * new Coordinate(-1, -45).isInGrid() -> false
     * new Coordinate(3, 3).isInGrid() -> false (as the 0 count, we subtract automatically one)
     * </code>
     * </pre>
     * 
     * 
     * @return
     *         <ul>
     *         <li>true if the coordinate is in the grid
     *         <li>false if the coordinate isn't in grid
     *         </ul>
     */
    public boolean isInGrid() {
        if (this.x < 0 || this.y < 0 || this.x > gridSize - 1 || this.y > gridSize - 1) {
            return false;
        }
        return true;
    }

    /**
     * Checks if this coordinate *is near from* the other coordinate. Means that
     * either the x or the y value, is a neighbor number (3 and 5 are neighbors of
     * 4) of the other. Fore more clarity, see the examples below.
     * 
     * <pre>
     * <code>
     * new Coordinate(0, 0).isNearFrom(new Coordinate(0, 1)) -> true
     * new Coordinate(0, 2).isNearFrom(new Coordinate(2, 0)) -> false
     * </code>
     * </pre>
     * 
     * @return
     *         <ul>
     *         <li>true if the coordinate is near from the other
     *         <li>false if the coordinate isn't near from the other
     *         </ul>
     * @param coordinates a coordinate object to compare
     */
    public boolean isNearFrom(Coordinate coordinates) {
        if (this.x == coordinates.getX() + 1 || this.x == coordinates.getX() - 1) {
            return true;
        }
        if (this.y == coordinates.getY() + 1 || this.x == coordinates.getY() - 1) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the x and y values are the same between the two coordinates.
     * Returns true as long as the x and y values are the same in both coordinates.
     * The two objects may not be exactly the same one.
     * 
     * @param coordinates the other to coordinate to compare with
     * @return whether the x and y values are the same in both coordinates.
     */
    public boolean propertiesEquals(Coordinate coordinates) {
        return this.x == coordinates.getX() && this.y == coordinates.getY() ? true : false;
    }

    /**
     * 
     * @param id the new id
     */
    public void setid(Long id) {
        this.id = id;
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
     * Returns a displayable version of thhis coordinate. <code>{X: 0, Y: 0}</code>
     * is an example.
     */
    @Override
    public String toString() {
        return "{X: " + this.x + ", Y: " + this.y + "}";
    }

    /**
     * @return the current grid size
     */
    public static int getGridSize() {
        return gridSize;
    }

    /**
     * Sets the current grid size used in some utility methods. The given value must
     * be strictly greater than 0.
     * 
     * @param value an integer bigger than 1
     */
    public static void setGridSize(int value) {
        if (value < 1) {
            return;
        }

        gridSize = value;
    }
}
