package com.martinheywang.products.model.template;

import com.martinheywang.products.model.template.Template.PointerType;

/**
 * Singleton class that allows the users to create templates easily
 * and quickly.
 * 
 * @author Martin Heywang
 */
public final class TemplateCreator {

	private static final TemplateCreator singleTon = new TemplateCreator();
	private PointerType[] pointers = new PointerType[4];

	private TemplateCreator() {
	}

	private void reset() {
		pointers = new PointerType[4];

		// Default value is NONE
		pointers[0] = PointerType.NONE;
		pointers[1] = PointerType.NONE;
		pointers[2] = PointerType.NONE;
		pointers[3] = PointerType.NONE;
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

	public TemplateCreator setTop(PointerType pointer) {
		pointers[0] = pointer;
		return this;
	}

	public TemplateCreator setRight(PointerType pointer) {
		pointers[1] = pointer;
		return this;
	}

	public TemplateCreator setBottom(PointerType pointer) {
		pointers[2] = pointer;
		return this;
	}

	public TemplateCreator setLeft(PointerType pointer) {
		pointers[3] = pointer;
		return this;
	}

	public TemplateCreator setAll(PointerType pointer) {
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
