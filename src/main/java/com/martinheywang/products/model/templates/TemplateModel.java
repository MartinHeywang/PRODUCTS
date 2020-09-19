package com.martinheywang.products.model.templates;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.direction.Direction;
import com.martinheywang.products.model.templates.Template.PointerTypes;

public class TemplateModel {

	private final PointerTypes[] pointers;

	TemplateModel(PointerTypes top, PointerTypes right,
			PointerTypes bottom, PointerTypes left) {
		this.pointers = new PointerTypes[4];

		this.pointers[0] = top;
		this.pointers[1] = right;
		this.pointers[2] = bottom;
		this.pointers[3] = left;
	}

	/**
	 * Creates a new {@link Template} according to the given coordinate and position
	 * 
	 * @param position  the position of the device that has this template
	 * @param direction the direction of the device that has this template
	 * @return the new template
	 */
	public Template create(Coordinate position, Direction direction) {
		switch (direction) {
		case UP:
			return new Template(position, this.pointers[0], this.pointers[1], this.pointers[2], this.pointers[3]);
		case RIGHT:
			return new Template(position, this.pointers[1], this.pointers[2], this.pointers[3], this.pointers[0]);
		case DOWN:
			return new Template(position, this.pointers[2], this.pointers[3], this.pointers[0], this.pointers[1]);
		case LEFT:
			return new Template(position, this.pointers[3], this.pointers[0], this.pointers[1], this.pointers[2]);
		default:
			return null;
		}
	}

}
