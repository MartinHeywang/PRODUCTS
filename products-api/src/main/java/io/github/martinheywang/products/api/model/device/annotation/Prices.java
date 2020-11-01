package io.github.martinheywang.products.api.model.device.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation will have an effect only on Device subclasses. It
 * determines the cost and the gain of the device at different moment.
 * 
 * @author Heywang
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Prices {

	String build();

	String upgradeTo2();

	String upgradeTo3();

	String destroyAt1();

	String destroyAt2();

	String destroyAt3();
}
