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

import io.github.martinheywang.products.api.persistance.Persistable;

import java.io.Serializable;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
public final class Coordinate implements Serializable, Persistable {

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
    private Long id;

    /**
     * X Value of the coordinate.
     */
    private IntegerProperty x;

    /**
     * Y value of the coordinate.
     */
    private IntegerProperty y;

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
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id){
        this.id = id;
    }

    /**
     * @return the x property
     */
    public ReadOnlyIntegerProperty xProperty() {
        return x;
    }

    /**
     * @return the y property
     */
    public ReadOnlyIntegerProperty yProperty() {
        return y;
    }

    /**
     * @return the x value of this coordinate.
     */
    public int getX() {
        return x.get();
    }

    /**
     * @return the y value of this coordinate.
     */
    public int getY() {
        return y.get();
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
     * new Coordinate(0, 0).isInGrid() -> always true, no matter the grid size
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
    public BooleanBinding isInGrid() {
        final BooleanProperty prop = new SimpleBooleanProperty(true);
        return prop.and(x.greaterThan(0)).and(y.greaterThan(0)).and(x.lessThan(gridSize)).and(y.lessThan(gridSize));
    }

    /**
     * Returns a boolean binding that checks if the coordinates has the same x and y
     * values. If the x of one coord changes, then the value may have changed.
     * 
     * @param coordinates the other to coordinate to compare with
     * @return whether the x and y values are the same in both coordinates.
     */
    public BooleanBinding propertiesEquals(Coordinate coord) {
        final BooleanBinding bind = Bindings.and(x.isEqualTo(coord.xProperty()), y.isEqualTo(coord.yProperty()));
        return bind;
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
