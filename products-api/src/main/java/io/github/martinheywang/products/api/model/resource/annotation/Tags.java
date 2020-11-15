package io.github.martinheywang.products.api.model.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation
 * for @{@link io.github.martinheywang.products.api.model.resource.annotation.Tag}
 * annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Tags {

    /**
     * The tags
     * 
     * @return the tags.
     */
    Tag[] value();

}
