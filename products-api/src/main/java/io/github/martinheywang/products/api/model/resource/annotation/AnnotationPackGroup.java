package io.github.martinheywang.products.api.model.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The container annotation for multiple {@link AnnotationPack}. Contais also a
 * target value, meaning this the group may be targeted for a specific device
 * class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(AnnotationPackGroups.class)
public @interface AnnotationPackGroup {

    /**
     * All the annotation pack of the group
     * 
     * @return the annotation packs
     */
    AnnotationPack[] value();

    /**
     * <p>
     * The target class (as a String) of the group.
     * </p>
     * <p>
     * This return a String and not a Class because resources plugin and kit
     * shouldn't not depend on a device plugin or kit.
     * </p>
     * 
     * @return the target device class.
     */
    String target();

}