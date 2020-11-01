package io.github.martinheywang.products.api.model.device.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation will have an effect only on Device subclasses. It
 * gives a name to the device
 * 
 * @author Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface AccessibleName {

	String value() default "No name provided.";
}
