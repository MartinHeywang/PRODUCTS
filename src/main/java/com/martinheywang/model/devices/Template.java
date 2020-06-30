package com.martinheywang.model.devices;

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
	// This constructor is 'private', because, this type can only be
	// instantiated by her nested class TemplateModel.
	private Template(Coordinate location, PointerTypes top, PointerTypes right,
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

	public final static class TemplateModel {
		// This array represents the type of the pointer of each side,
		// considering that the device is oriented UP
		PointerTypes[] pointers = new PointerTypes[4];

		/**
		 * Creates a new TemplateModel, that can create a Template.
		 * 
		 * @param pointers the pointers type of each side of the device
		 *                 (respectively: TOP, RIGHT, BOTTOM, then LEFT).
		 */
		public TemplateModel(PointerTypes top, PointerTypes right,
				PointerTypes bottom, PointerTypes left) {
			this.pointers[0] = top;
			this.pointers[1] = right;
			this.pointers[2] = bottom;
			this.pointers[3] = left;
		}

		/**
		 * Creates a Template with the values of this model, but rotating it.
		 * 
		 * @param direction the Direction of the Template
		 * @param location  the current location of this template's device
		 * @return the new template.
		 */
		public Template createTemplate(Coordinate location,
				Direction direction) {
			switch (direction) {
			case UP:
				// The template model is based on the direction UP, so we create
				// a new Template with the same values as this model.
				return new Template(location, pointers[0], pointers[1],
						pointers[2], pointers[3]);
			case RIGHT:
				// Here each values are exchanged by -1 (index 1 (UP) becomes
				// index 0(RIGHT))
				return new Template(location, pointers[1], pointers[2],
						pointers[3],
						pointers[0]);
			case DOWN:
				// Here each values are exchanged by +2 (index 1 (UP) becomes
				// index 3(DOWN))
				return new Template(location, pointers[2], pointers[3],
						pointers[0],
						pointers[1]);
			case LEFT:
				// Here each values are exchanged by +1 (index 1 (UP) becomes
				// index 2(LEFT))
				return new Template(location, pointers[3], pointers[0],
						pointers[1],
						pointers[2]);
			default:
				// Default case return null to avoid warnings
				return null;
			}
		}
	}

}
