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

import static io.github.martinheywang.products.api.model.template.Template.PointerType.NONE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * <p>
 * This annotation will only take effect on {@link io.github.martinheywang.products.api.model.device.Device} subclasses.
 * It defines the default template of a device, when its build.
 * More informations about templates can be found {@link io.github.martinheywang.products.api.model.template.Template here}.
 * </p>
 * For each value (top, right, bottom, left), you may define a {@link io.github.martinheywang.products.api.model.template.Template.PointerType}.
 * The values that are not defined are set to NONE (neither entry or exit) by default.
 * Those values will be rotated according to the device direction. 
 * Simply set those values to what you can see on the device image 
 * <strong>without any rotation</strong>.
 * <p>
 * For example, a conveyor has an entry at top and an exit at bottom. The class is marked just like that :
 * <code>@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)</code>
 * </p>
 * <p>If you do not explicitly define a default template on the device, all values will be NONE, 
 * meaning that your device won't be ever triggered, as it hasn't got any entry.
 * </p>
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface DefaultTemplate {

    /**
     * The top value
     * 
     * @return the top's pointer type
     */
    PointerType top() default NONE;

    /**
     * The right value
     * 
     * @return the right's pointer type
     */
    PointerType right() default NONE;

    
    /**
     * The bottom value.
     * 
     * @return the bottom's pointer type.
     */
    PointerType bottom() default NONE;

    /**
     * The left value.
     * 
     * @return the left's pointer type.
     */
    PointerType left() default NONE;
}
