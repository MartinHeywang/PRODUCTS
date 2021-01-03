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
package io.github.martinheywang.products.api.model.properties;

import io.github.martinheywang.products.api.model.direction.Direction;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The SimpleDirectionProperty is a class that 'gives a name' to the the generic
 * {@link SimpleObjectProperty}, giving as type argument {@link Direction}.
 * The purpose of giving a name is to create a persister for ormlite.
 * 
 * @author Martin Heywang
 */
public final class SimpleDirectionProperty extends SimpleObjectProperty<Direction> {

    public SimpleDirectionProperty(Direction direction) {
        super(direction);
    }
}
