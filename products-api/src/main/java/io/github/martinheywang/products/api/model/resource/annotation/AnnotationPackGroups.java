package io.github.martinheywang.products.api.model.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The container annotation for {@link AnnotationPackGroup}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnnotationPackGroups {
    
    /**
     * The annotations of the group
     * 
     * @return the annotation
     */
    AnnotationPackGroup[] value();

}
