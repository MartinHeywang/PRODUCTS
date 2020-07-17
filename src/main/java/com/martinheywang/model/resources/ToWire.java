package com.martinheywang.model.resources;

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
public @interface ToWire {

	/**
	 * <p>
	 * Returns the full doted path of the filed that represents this
	 * Resource after transformation.
	 * </p>
	 * <p>
	 * For example:
	 * <code>com.username.app.plugin.YourClass.SomeSortOfField</code>
	 * </p>
	 * 
	 * @return the full doted path to the field
	 */
	String to();

}
