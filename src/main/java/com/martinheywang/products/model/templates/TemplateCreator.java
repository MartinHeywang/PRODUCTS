package com.martinheywang.products.model.templates;

import com.martinheywang.products.model.templates.Template.PointerTypes;

/**
 * Singleton class that allows the users to create templates easily
 * and quickly.
 * 
 * @author Martin Heywang
 */
public final class TemplateCreator {

	private static final TemplateCreator singleTon = new TemplateCreator();
	private PointerTypes[] pointers = new PointerTypes[4];

	private TemplateCreator() {
	}

	private void reset() {
		pointers = new PointerTypes[4];

		// Default value is NONE
		pointers[0] = PointerTypes.NONE;
		pointers[1] = PointerTypes.NONE;
		pointers[2] = PointerTypes.NONE;
		pointers[3] = PointerTypes.NONE;
	}

	/**
	 * Returns the created template using the given location <strong>and
	 * resets the values</strong>.
	 * 
	 * @param location the position of the owner of this Template.
	 * @return the created template.
	 */
	public TemplateModel getModel() {
		TemplateModel templateModel = new TemplateModel(pointers[0],
				pointers[1],
				pointers[2], pointers[3]);
		reset();
		return templateModel;
	}

	public TemplateCreator setTop(PointerTypes pointer) {
		pointers[0] = pointer;
		return this;
	}

	public TemplateCreator setRight(PointerTypes pointer) {
		pointers[1] = pointer;
		return this;
	}

	public TemplateCreator setBottom(PointerTypes pointer) {
		pointers[2] = pointer;
		return this;
	}

	public TemplateCreator setLeft(PointerTypes pointer) {
		pointers[3] = pointer;
		return this;
	}

	public TemplateCreator setAll(PointerTypes pointer) {
		pointers[0] = pointer;
		pointers[1] = pointer;
		pointers[2] = pointer;
		pointers[3] = pointer;
		return this;
	}

	public static final TemplateCreator getSingleton() {
		return singleTon;
	}

}
