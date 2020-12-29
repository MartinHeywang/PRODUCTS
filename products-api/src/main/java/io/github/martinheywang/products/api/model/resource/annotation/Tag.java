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
package io.github.martinheywang.products.api.model.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * The Tag annotation has an effect only on
 * {@link io.github.martinheywang.products.api.model.resource.Resource} enum
 * constants.
 * </p>
 * <p>
 * It allows to specify for a given property a given value, that may be
 * applicable for the given class.
 * </p>
 * <p>
 * When getting the value, you may need to parse it, as it is a {@link String}.
 * You may also need to check the property
 * </p>
 */
@Repeatable(Tags.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Tag {

    /**
     * The device class that will be concerned by the property. If all type are
     * concerned, put <code>-all-</code>.
     * 
     * @return the device class concerned by this property.
     */
    String clazz() default "-all-";

    /**
     * The property name.
     * 
     * @return the property name.
     */
    String property();

    /**
     * The value
     * 
     * @return the value of the {@link #property()}
     */
    String value();
}
