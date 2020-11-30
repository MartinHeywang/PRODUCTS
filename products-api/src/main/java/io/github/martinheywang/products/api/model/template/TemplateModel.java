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
 * <p>
 * A TemplateModel is a kind of model that doesn't depends on a
 * {@link io.github.martinheywang.products.api.model.direction.Direction}
 * (rotation) and so on
 * {@link io.github.martinheywang.products.api.model.device.Device}. It looks
 * like a {@link Template} and assumes for the values (top, right, bottom, left)
 * that the
 * {@link io.github.martinheywang.products.api.model.direction.Direction} on a
 * regular template would be equal to
 * {@link io.github.martinheywang.products.api.model.direction.Direction#UP}.
 * </p>
 * <p>
 * You can't actually instantiate the TemplateModel class. But you can create a
 * TemplateModel using the {@link TemplateCreator} class or by putting the
 * {@link io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate}
 * annotation on top of a Device subclass.
 * </p>
 */
public class TemplateModel {

    private final PointerType[] pointers;

    TemplateModel(PointerType top, PointerType right, PointerType bottom, PointerType left) {
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

    /**
     * @return the top
     */
    public PointerType getTop() {
        return this.pointers[0];
    }

    /**
     * 
     * @return the right
     */
    public PointerType getRight() {
        return this.pointers[1];
    }

    /**
     * 
     * @return the bottom
     */
    public PointerType getBottom() {
        return this.pointers[2];
    }

    /**
     * 
     * @return the left
     */
    public PointerType getLeft() {
        return this.pointers[3];
    }

    /**
     * Counts how many pointers how the given type there are in this template (min:
     * 0, max: 4).
     * 
     * @param type a type of pointer
     * @return the count of the pointer with the given type.
     */
    public Integer countOf(PointerType type) {
        int count = 0;
        if (this.getTop() == type) {
            count++;
        }
        if (this.getRight() == type) {
            count++;
        }
        if (this.getBottom() == type) {
            count++;
        }
        if (this.getLeft() == type) {
            count++;
        }

        return count;
    }
}