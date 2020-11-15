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
 * This annotation will only take effect on {@link io.github.martinheywang.products.api.model.device.Device} subclasses. 
 * It defines the action cost of a device, how many it spends doing its thing.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface ActionCost {
    
    /**
     * The cost of the action of this device.
     * 
     * @return the value, the cost
     */
    String value() default "5";
}
