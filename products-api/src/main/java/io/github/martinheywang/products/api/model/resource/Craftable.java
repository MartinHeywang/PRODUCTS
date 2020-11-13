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
 * The field marked with this annotation can be crafted.
 * </p>
 * <p>
 * This annotation has an effect only on field of type
 * <code>com.martinheywang.model.resources.Resource</code>. This type
 * should be an enum.
 * </p>
 * 
 * @author Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Craftable {

	/**
	 * Returns the recipe as an array of remote packs.
	 * 
	 * @return the recipe
	 */
	RemotePack[] recipe();

	/**
	 * A RemotePack is the annotation version of a {@link io.github.martinheywang.products.api.model.Pack}.
	 * It doesn't have any processors, it only has an effect when it is used in a the {@link Craftable} annotation.
	 */
	@Retention(RUNTIME)
	@Target(FIELD)
	public @interface RemotePack {

		/**
		 * Returns the class in which the requested field can be found.
		 * 
		 * @return the class
		 */
		Class<? extends Resource> clazz();

		/**
		 * Returns the name of the field in the class given by the attribute
		 * 'clazz'.
		 * 
		 * @return the name of the field
		 */
		String field();

		/**
		 * Returns the quantity as a parsable String to the BigInteger
		 * constructor. Default is set to one.
		 * 
		 * @return the quantity as a String.
		 */
		String quantity() default "1";

	}

}