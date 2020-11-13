/*
   Copyright 2020 Martin Heywang

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

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.direction.Direction;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * The TemplateModel class is class that allows the creation of template using a 'model'. 
 * This model should be created first, and can create an actual Template using the 
 * {@link io.github.martinheywang.products.api.model.direction.Direction} 
 * of the device asking for one.
 */
public class TemplateModel {

    private final PointerType[] pointers;

    TemplateModel(PointerType top, PointerType right,
	    PointerType bottom, PointerType left) {
	this.pointers = new PointerType[4];

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
