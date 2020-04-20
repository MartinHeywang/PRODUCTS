package com.martin.model.appareils;

import java.util.HashMap;

import com.martin.model.Coordinates;

public class Template {

	// This array represents the type of each the side
	// The first is top, the second is left, the third is bottom (like for
	// padding and margin in CSS)
	PointerTypes[] pointerTypes = { PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE, PointerTypes.NONE };
	Coordinates[] pointers = { null, null, null, null };

	HashMap<PointerTypes, Coordinates> pointersHash = new HashMap<PointerTypes, Coordinates>();

	/**
	 * Creates a new Template with an array of PointerTypes.
	 * 
	 * @param location the current position
	 * @param pointers the type of all pointers
	 */
	// This constructor is 'private', because, this type can only be
	// instantiated by her nested class TemplateModel.
	private Template(Coordinates location, PointerTypes... pointers) {
		this.pointerTypes = pointers;

		// The pointer array should not be longer than 4 (each device has 4
		// sides only)
		// Even if there is a rest, we don't consider it.
		pointersHash.put(pointers[0], new Coordinates(location.getX(),
				location.getY() - 1));
		pointersHash.put(pointers[1], new Coordinates(location.getX() + 1,
				location.getY()));
		pointersHash.put(pointers[2], new Coordinates(location.getX(),
				location.getY() + 1));
		pointersHash.put(pointers[3], new Coordinates(location.getX() - 1,
				location.getY()));

		System.out.println(pointersHash.get(PointerTypes.EXIT));
	}

	public PointerTypes[] getPointerTypes() {
		return pointerTypes;
	}

	/**
	 * 
	 * @return the pointers
	 */
	public Coordinates[] getPointers() {
		return pointers;
	}

	// A little enum who distinct the different pointers type
	enum PointerTypes {
		// Neither entry or exit
		NONE,
		// Only exit
		EXIT,
		// Only entry
		ENTRY;

		/*
		 * I did not added a case BOTH, for both exit and entry, because it
		 * wasn't useful when I created this enum.
		 */
	}

	final static class TemplateModel {
		// This array represents the type of the pointer of each side,
		// considering that the device is oriented UP
		PointerTypes[] pointers = { PointerTypes.NONE, PointerTypes.NONE,
				PointerTypes.NONE, PointerTypes.NONE };

		/**
		 * Creates a new TemplateModel, that can create a Template.
		 * 
		 * @param pointers the pointers type of each side of the device
		 *                 (respectively: TOP, RIGHT, BOTTOM, then LEFT).
		 */
		public TemplateModel(PointerTypes... pointers) {
			this.pointers = pointers;
		}

		/**
		 * Creates a Template with the values of this model, but rotating it.
		 * 
		 * @param direction the Direction of the Template
		 * @param location  the current location of this template's device
		 * @return the new template.
		 */
		public Template createTemplate(Coordinates location,
				Direction direction) {
			switch (direction) {
			case UP:
				// The template model is based on the direction UP, so we create
				// a new Template with the same values as this model.
				return new Template(location, pointers);
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
