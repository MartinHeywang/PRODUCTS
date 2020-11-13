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
package io.github.martinheywang.products.api.model.device.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation will have an effect only on Device subclasses.
 * </p>
 * <p>
 * Defines on a device tha cost and the gain for different actions of the user, 
 * like building, upgrading, destroying.
 * </p>
 * 
 * @author Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Prices {

    /**
     * The build price.
     * 
     * @return the build price.
     */
    String build();

    /**
     * The upgrading price (to level 2)
     * 
     * @return the price of upgrading to level 2
     */
    String upgradeTo2();

    /**
     * The upgrading price (to level 3)
     * 
     * @return the price of upgrading to level 3
     */
    String upgradeTo3();

    /**
     * The destroying gain (at level 1)
     * 
     * @return the gain of destroying at level 1
     */
    String destroyAt1();

    /**
     * The destroying gain (at level 2)
     * 
     * @return the gain of destroying at level 2
     */
    String destroyAt2();

    /**
     * The destroying gain (at level 3)
     * 
     * @return the gain of destroying at level 3
     */
    String destroyAt3();
}
