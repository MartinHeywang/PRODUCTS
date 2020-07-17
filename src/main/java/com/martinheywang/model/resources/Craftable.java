package com.martinheywang.model.resources;

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
	 * <p>
	 * Returns a parsable String of the recipe of this Resource. A Recipe
	 * is basically an array of packs, that put together a Resource and a
	 * quantity.
	 * </p>
	 * <p>
	 * Rules:
	 * <ul>
	 * <li>Each Pack must begin with its quantity</li>
	 * <li>Insert a '|' between the quantity and the Resource</li>
	 * <li>Each Resource of a pack must be given by its full name.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * To make things clearer, here is an example on what is expected:
	 * <code>@Craftable(recipe = 
	 * {"2|com.martinheywang.model.resources.Wire.COPPER_WIRE", 
	 * "1|com.martinheywang.model.resources.Ore.GOLD"})</code>
	 * </p>
	 * <p>
	 * Of course, if the quantity reaches zero, the pack is ignored.
	 * </p>
	 * <p>
	 * (I regret that I had to do it that way, but I didn't find anything
	 * else better).
	 * </p>
	 * 
	 * @return the recipe as a string
	 */
	String[] recipe();

}