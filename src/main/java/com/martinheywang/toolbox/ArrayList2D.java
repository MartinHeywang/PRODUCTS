package com.martinheywang.toolbox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * The ArrayList2D class represents a bi-dimensionnal extensible list.
 * This class is adapted to stay as a square : it has the same number
 * of rows and columns, to simplify the code.
 * </p>
 * <p>
 * It combines the advantages of an array list that can be extensible,
 * so you can add elements. But it acts as a bi-dimensionnal array
 * declared like this :
 * 
 * <pre>
 * <code>
 * T[][] = {{}, {}} // For example
 * </code>
 * </pre>
 * </p>
 * 
 * @author Martin Heywang
 *
 * @param <T> the type of objects stored by this object
 */
public class ArrayList2D<T> {

    /**
     * The grid handled by this class
     */
    /*
     * I didn't find a better way of doing a list of lists to make an
     * extensible bi-dimensionnal list.
     */
    private final ArrayList<ArrayList<T>> grid = new ArrayList<>();

    /**
     * Creates an empty bi-dimensionnal list.
     */
    public ArrayList2D() {
    }

    /**
     * Creates a new bi-dimensionnal list, filled with a null value.
     * 
     * @param object the object to fill the list with
     * @param size   the size of the bi-dimmensionnal list
     */
    public ArrayList2D(int size) {

	for (int x = 0; x < size; x++) {

	    final ArrayList<T> row = new ArrayList<>();

	    for (int y = 0; y < size; y++) {
		row.add(null);
	    }

	    grid.add(row);
	}
    }

    /**
     * Creates a new bi-dimensionnal list, filled to the given size with
     * the given object.
     * 
     * @param object the object to fill the list with
     * @param size   the size of the bi-dimmensionnal list
     */
    public ArrayList2D(T object, int size) {

	for (int x = 0; x < size; x++) {

	    final ArrayList<T> row = new ArrayList<>();

	    for (int y = 0; y < size; y++) {
		row.add(object);
	    }

	    grid.add(row);
	}
    }

    /**
     * Returns the given object at the given x and y indexes.
     * 
     * @param x the x index
     * @param y the y index
     * @return the object found at those indexes
     */
    public T get(int x, int y) {
	try {
	    return grid.get(x).get(y);
	} catch (final IndexOutOfBoundsException e) {
	    return null;
	}
    }

    /**
     * Sets at the given indexes the given object
     * 
     * @param object the object to set
     * @param x      the x index
     * @param y      the y index
     */
    public void set(T object, int x, int y) {
	grid.get(x).set(y, object);
    }

    /**
     * Adds the given object at the given indexes. Act as
     * {@link #set(Object, int, int)}, but ensure that there are enough
     * columns and rows to put the object where we want it to be.
     * 
     * @param object the object to add
     * @param x      the x index
     * @param y      the y index
     */
    public void add(T object, int x, int y) {
	while (size() < Math.max(x + 1, y + 1)) {
	    addRowAndColumn();
	}
	this.set(object, x, y);
    }

    /**
     * Returns the size of the bi-dimensionnal list.
     * 
     * @return the size
     */
    public int size() {
	return grid.size();
    }

    /**
     * Adds a row and a column to the bi-dimensionnal list.
     * 
     * @param object the object to fill the new cases with
     * @return the new size of the array
     */
    public int addRowAndColumn(T object) {
	grid.add(new ArrayList<T>());

	for (int x = 0; x < size(); x++) {

	    while (grid.get(x).size() != size()) {

		grid.get(x).add(object);
	    }
	}

	return size();
    }

    /**
     * Adds a row and a column to the bi-dimensionnal list.
     * 
     * @return the new size of the array
     */
    public int addRowAndColumn() {
	grid.add(new ArrayList<T>());

	for (int x = 0; x < size(); x++) {

	    while (grid.get(x).size() != size()) {

		grid.get(x).add(null);
	    }
	}

	return size();
    }

    /**
     * Fills all the empty spaces in the array with the given object. An
     * case is marked as empty when this condition is present:
     * 
     * <pre>
     * <code>
     * if(obj == null){
     * 	// Then make the changement
     * }
     * </code>
     * </pre>
     * 
     * @param object The object to fill the empty spaces with
     */
    public void fill(T object) {
	for (final ArrayList<T> rows : grid) {
	    for (T t : rows) {
		if (t == null) {
		    t = object;
		}
	    }
	}
    }

    /**
     * Fills all the empty spaces in the array with the given object. An
     * case is marked as empty when this condition is present:
     * 
     * <pre>
     * <code>
     * if(obj == null){
     * 	// Then make the changement
     * }
     * </code>
     * </pre>
     * 
     * @param object The object to fill the empty spaces with
     * @param size   the new size
     */
    public void fill(T object, int size) {
	while (size > size()) {
	    addRowAndColumn();
	}

	for (final ArrayList<T> rows : grid) {
	    for (T t : rows) {
		if (t == null) {
		    t = object;
		}
	    }
	}
    }

    /**
     * Returns a collections with all the objects stored in the
     * bi-dimensionnal list
     * 
     * @return a collection
     */
    public Collection<T> toCollection() {

	final ArrayList<T> array = new ArrayList<>();

	for (final ArrayList<T> lists : grid) {

	    array.addAll(lists);
	}

	return array;
    }

}
