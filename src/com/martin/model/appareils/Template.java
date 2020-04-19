package com.martin.model.appareils;

public class Template {

	// This array represents the type of each the side
	// The first is top, the second is left, the third is bottom (like for
	// padding and margin in CSS)
	PointerTypes[] pointers = { PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE, PointerTypes.NONE };

	protected Template(PointerTypes... pointers) {
		this.pointers = pointers;
	}

	// A little enum who distinct the different pointers type
	enum PointerTypes {
		// Neither entry or exit
		NONE,
		// Only exit
		EXIT,
		// Only entry
		ENTRY,
		// Both exit and entry
		BOTH;
	}

	final static class TemplateModel {
		// This array represents the type of the pointer of each side,
		// considering that the device is oriented UP
		PointerTypes[] pointers = { PointerTypes.NONE, PointerTypes.NONE,
				PointerTypes.NONE, PointerTypes.NONE };

		public TemplateModel(PointerTypes... pointers) {
			this.pointers = pointers;
		}

		/**
		 * Creates a Template with the values of this model, but rotating it.
		 * 
		 * @param direction the Direction of the Template
		 * @return the new template.
		 */
		public Template createTemplate(Direction direction) {
			Template template = null;
			switch (direction) {
			case UP:
				// The template model is based on the direction UP, so we create
				// a new Template with the same values as this model.
				template = new Template(pointers);
				break;
			case RIGHT:
				// Here each values are exchanged by -1 (index 1 (UP) becomes
				// index 0(RIGHT))
				template = new Template(pointers[1], pointers[2], pointers[3],
						pointers[0]);
				break;
			case DOWN:
				// Here each values are exchanged by +2 (index 1 (UP) becomes
				// index 3(DOWN))
				template = new Template(pointers[2], pointers[3], pointers[0],
						pointers[1]);
				break;
			case LEFT:
				// Here each values are exchanged by +1 (index 1 (UP) becomes
				// index 2(RIGHT))
				template = new Template(pointers[3], pointers[0], pointers[1],
						pointers[2]);
				break;
			default:
				break;
			}
			return template;
		}
	}

}
