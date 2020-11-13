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
package io.github.martinheywang.products.api.model.resource;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * The field marked with this annotation can be transformed into a
 * wire.
 * </p>
 * 
 * @author Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface ToWire {

	/**
	 * Returns the class where can be found the given field name (in
	 * field()). You probably want to define this Resource in a proper
	 * enum named Wire.
	 * 
	 * @return the class of the field
	 */
	Class<? extends Resource> clazz();

	/**
	 * Returns the name of the field that will be search in the given
	 * class (in clazz()).
	 * 
	 * @return the name of the field
	 */
	String field();

}
