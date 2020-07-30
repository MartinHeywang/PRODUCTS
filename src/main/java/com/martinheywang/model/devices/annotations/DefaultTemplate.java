package com.martinheywang.model.devices.annotations;

import static com.martinheywang.model.templates.Template.PointerTypes.NONE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.martinheywang.model.templates.Template.PointerTypes;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface DefaultTemplate {

	PointerTypes top() default NONE;

	PointerTypes right() default NONE;

	PointerTypes bottom() default NONE;

	PointerTypes left() default NONE;
}
