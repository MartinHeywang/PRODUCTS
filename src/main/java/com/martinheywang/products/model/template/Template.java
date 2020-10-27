package com.martinheywang.products.model.template;

import java.util.ArrayList;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.device.Device;
import com.martinheywang.products.toolbox.LocatedHashMap;

/**
 * A template in PRODUCTS. is a way to represent the entries and exits of a
 * {@link Device}. It contains the necessary informarion about which type of
 * connection it is (entry, exit, none), and where it points.
 * 
 * @author Martin HEYWANG
 */
public class Template {

    public enum PointerType {
	/**
	 * Neither entry or exit
	 */
	NONE,
	/**
	 * Only exit
	 */
	EXIT,
	/**
	 * Only entry
	 */
	ENTRY;
    }

    // This array represents the type of each the side
    // The first is top, the second is left, the third is bottom (like
    // padding and margin in CSS)
    private final LocatedHashMap<PointerType, Coordinate> pointers = new LocatedHashMap<>();

    /**
     * Creates a new Template with an array of PointerTypes.
     * 
     * @param location the current position
     * @param pointers the type of all pointers
     */
    // This constructor is only in the 'package', as only the
    // TemplateCreator class should be able to create some.
    Template(Coordinate location, PointerType top, PointerType right,
	    PointerType bottom, PointerType left) {
	// We put in the HashMap the coordinates, according to the location
	this.pointers.put(top,
		new Coordinate(location.getX(), location.getY() - 1));
	this.pointers.put(right,
		new Coordinate(location.getX() + 1, location.getY()));
	this.pointers.put(bottom,
		new Coordinate(location.getX(), location.getY() + 1));
	this.pointers.put(left,
		new Coordinate(location.getX() - 1, location.getY()));
    }

    /**
     * 
     * @return the pointers
     */
    public LocatedHashMap<PointerType, Coordinate> getPointers() {
	return this.pointers;
    }

    /**
     * Little shortcut for
     * <code>this.getPointers().get(PointerTypes)</code>.
     * 
     * @param type the key in the HashMap
     * @return a list of pointers
     */
    public ArrayList<Coordinate> getPointersFor(PointerType type) {
	return this.pointers.get(type);
    }

    @Override
    public String toString() {
	return "NONE:" + this.getPointersFor(PointerType.NONE) + "\n" + "ENTRY:"
		+ this.getPointersFor(PointerType.ENTRY) + "\n" + "EXIT:" + this.getPointersFor(PointerType.EXIT);
    }

}
