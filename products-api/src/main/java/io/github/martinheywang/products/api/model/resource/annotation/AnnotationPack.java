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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * <p>
 * The annotation pack class defines a special version of a
 * {@link io.github.martinheywang.products.api.model.Pack}.
 * </p>
 * <p>
 * It only applies on Resource enums constants.
 * </p>
 * <p>
 * It can easily be converted to a regular pack using
 * {@link io.github.martinheywang.products.api.utils.PackUtils#toPack(io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack)}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnnotationPack {

    /**
     * The class where the {@link #field()} can be found.
     * 
     * @return the class
     */
    Class<? extends Resource> clazz();

    /**
     * The field name in the given {@link #clazz()}.
     * 
     * @return the field name
     */
    String field();

    /**
     * The quantity of the pack. Default is one.
     * 
     * @return the quantity
     */
    String quantity() default "1";
}
