package io.github.martinheywang.products.api.model.device.annotation;

import static io.github.martinheywang.products.api.model.template.Template.PointerType.NONE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.martinheywang.products.api.model.template.Template.PointerType;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface DefaultTemplate {

	PointerType top() default NONE;

	PointerType right() default NONE;

	PointerType bottom() default NONE;

	PointerType left() default NONE;
}
