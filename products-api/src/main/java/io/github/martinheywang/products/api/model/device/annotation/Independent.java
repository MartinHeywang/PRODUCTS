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
package io.github.martinheywang.products.api.model.device.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * Will only work on {@link io.github.martinheywang.products.api.model.device.Device} subclasses.
 * </p>
 * <p>
 * Each instance of a class marked with this annotation will be indepedent,
 * meaning that the device will trigger automatically each iteration and won't
 * make use of the given resources as arguments. The given resources will
 * equal to <code>null</code>, so make sure you don't use them.
 * </p>
 * 
 * @author Martin Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Independent {

    /**
     * The value, whether this device is independent.
     * 
     * @return the value, the independent property.
     */
    boolean value() default true;
}
