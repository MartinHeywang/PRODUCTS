package com.martinheywang.products.model.resource;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * The field marked with this annotation can be crafted.
 * </p>
 * </p>
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