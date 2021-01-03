/*
   Copyright 2021 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.model.template;

import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * 
 * Singleton class that allows the users to create templates easily and quickly.
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
	 * Creates and returns the {@link TemplateModel} according the values previously
	 * sets. Once created, resets the values.
	 * 
	 * @return the created template model.
	 */
	public TemplateModel getModel() {
		TemplateModel templateModel = new TemplateModel(pointers[0], pointers[1], pointers[2], pointers[3]);
		reset();
		return templateModel;
	}

	/**
	 * Sets the top value of the next model created.
	 * 
	 * @param pointer the pointer type to set.
	 * @return this object.
	 */
	public TemplateCreator setTop(PointerType pointer) {
		pointers[0] = pointer;
		return this;
	}

	/**
	 * Sets the right value of the next model created.
	 * 
	 * @param pointer the pointer type to set
	 * @return this object.
	 */
	public TemplateCreator setRight(PointerType pointer) {
		pointers[1] = pointer;
		return this;
	}

	/**
	 * Sets the bottom of the next model created.
	 * 
	 * @param pointer the pointer type to set
	 * @return this object.
	 */
	public TemplateCreator setBottom(PointerType pointer) {
		pointers[2] = pointer;
		return this;
	}

	/**
	 * Sets the left value of the next model created.
	 * 
	 * @param pointer the pointer type to set
	 * @return this object.
	 */
	public TemplateCreator setLeft(PointerType pointer) {
		pointers[3] = pointer;
		return this;
	}

	/**
	 * Sets all values (top, right, bottom and left) to the given value.
	 * 
	 * @param pointer the pointer type value
	 * @return this object.
	 */
	public TemplateCreator setAll(PointerType pointer) {
		pointers[0] = pointer;
		pointers[1] = pointer;
		pointers[2] = pointer;
		pointers[3] = pointer;
		return this;
	}

	/**
	 * Creates a {@link TemplateModel} using the
	 * {@link io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate}.
	 * 
	 * @param annotation the annotation to create the model.
	 * @return the brand new created template model
	 */
	public static TemplateModel withAnnotation(DefaultTemplate annotation) {
		return getSingleton().setTop(annotation.top()).setRight(annotation.right()).setBottom(annotation.bottom())
				.setLeft(annotation.left()).getModel();
	}

	/**
	 * 
	 * @return the singleton.
	 */
	public static final TemplateCreator getSingleton() {
		return singleTon;
	}

}
