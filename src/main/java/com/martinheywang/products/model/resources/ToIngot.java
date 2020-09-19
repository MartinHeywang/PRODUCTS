package com.martinheywang.products.model.resources;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * The field marked with this annotation can be transformed into an
 * ingot.
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
public @interface ToIngot {

	/**
	 * Returns the class where can be found the given field name (in
	 * field()). You probably want to define this Resource in a proper
	 * enum named Ingot.
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
