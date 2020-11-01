package io.github.martinheywang.products.api.model.device.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * Will only work on Device subclasses.
 * </p>
 * <p>
 * Each instance of a class marked with this annotation will be indepedent,
 * meaning that the device will trigger automatically each iteration and won't
 * make use of the given resources as arguments. The given resources are
 * strictly equal to <code>null</code>.
 * </p>
 * 
 * @author Martin Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Independent {

    boolean value() default true;
}
