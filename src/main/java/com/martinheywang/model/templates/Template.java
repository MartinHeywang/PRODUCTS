package com.martinheywang.model.templates;

import java.util.ArrayList;

import com.martinheywang.model.Coordinate;
import com.martinheywang.toolbox.LocatedHashMap;

public class Template {

	// This array represents the type of each the side
	// The first is top, the second is left, the third is bottom (like
	// padding and margin in CSS)
	private LocatedHashMap<PointerTypes, Coordinate> pointers = new LocatedHashMap<>();

	/**
	 * Creates a new Template with an array of PointerTypes.
	 * 
	 * @param location the current position
	 * @param pointers the type of all pointers
	 */
	// This constructor is only in the 'package', as only the
	// TemplateCreator class should be able to create some.
	Template(Coordinate location, PointerTypes top, PointerTypes right,
			PointerTypes bottom, PointerTypes left) {
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
	public LocatedHashMap<PointerTypes, Coordinate> getPointers() {
		return pointers;
	}

	/**
	 * Little shortcut for
	 * <code>this.getPointers().get(PointerTypes)</code>.
	 * 
	 * @param type the key in the HashMap
	 * @return a list of pointers
	 */
	public ArrayList<Coordinate> getPointersFor(PointerTypes type) {
		return pointers.get(type);
	}

	// A little enum who distinct the different pointers type
	public enum PointerTypes {
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
		ENTRY,
		/**
		 * Both entry and exit
		 */
		BOTH; // The const enum BOTH is not really useful, but I added it in
				// case that someone will add a feature that uses it.
	}

}
