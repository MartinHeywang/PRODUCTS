package com.martinheywang.model.templates;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.templates.Template.PointerTypes;

public class TemplateModel {

	private final PointerTypes[] pointers;

	TemplateModel(PointerTypes top, PointerTypes right,
			PointerTypes bottom, PointerTypes left) {
		pointers = new PointerTypes[4];

		pointers[0] = top;
		pointers[1] = right;
		pointers[2] = bottom;
		pointers[3] = left;
	}

	public Template create(Coordinate position) {
		return new Template(position, pointers[0], pointers[1], pointers[2],
				pointers[3]);
	}

}
