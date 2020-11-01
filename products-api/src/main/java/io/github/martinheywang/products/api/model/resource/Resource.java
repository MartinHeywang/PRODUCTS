package io.github.martinheywang.products.api.model.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigInteger;

import org.pf4j.ExtensionPoint;

/**
 * 
 * <p>
 * This interface represents a <em>Resource</em> that can be used by all the
 * devices. It extends {@link Displayable}, that does mean that it provides a
 * common displayer for the Resource, that you can override.
 * </p>
 * <p>
 * It manages also all the Resource in the game, with it List. You can directly
 * found this list by calling {@link Resource#getReferences()}.
 * </p>
 * <p>
 * You can register an enum type (extending Resource) by calling
 * {@link #register(Class)}.
 * </p>
 * <p>
 * When registering, make sure to add some of the annotations on your fields
 * provided by the API such as {@link Buyable @Buyable},
 * {@link Craftable @Craftable}, or {@link ToWire @ToWire}. This will add the
 * resources automatically (as long as you register the class) according to the
 * annotations. But still, you can do that manually by calling
 * {@link #addReferences(Resource...)}.
 * </p>
 * <p>
 * By the way, you can also remove references by calling
 * {@link #removeReferences(Resource...)}.
 * </p>
 * 
 * 
 * @author Martin Heywang
 *
 */
public interface Resource extends ExtensionPoint {

    /**
     * Returns the displayable name of the resource.
     * 
     * @return the name
     */
    public String getName();

    /**
     * Returns the value (the sell-price) of the resource.
     * 
     * @return
     */
    public BigInteger getPrice();

    /**
     * <p>
     * Returns a String representing a relative path to the view representing this
     * resource.
     * </p>
     * <p>
     * For example, the following expression :
     * <code>this.getClass().getResourceAsStream(this.getURL())</code> must not be
     * null, where the url is the value returned by the <code>getURL()</code>
     * method, and <code>this.getClass()</code> the class where the valeu is
     * defined.
     * </p>
     * 
     * @return the local url of the image
     */
    public String getURL();

    /**
     * Returns true if this Resource object owns the given annotation. It allows you
     * to check if this Resource object is Buyable, for instance.
     * 
     * @param annotation
     * @return
     */
    public default boolean hasAnnotation(Class<? extends Annotation> annotation) {
	try {
	    return this.getClass().getField(toString()).isAnnotationPresent(annotation);
	} catch (NoSuchFieldException | SecurityException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public default Field getField() {
	try {
	    return this.getClass().getField(toString());
	} catch (NoSuchFieldException | SecurityException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public default String toText() {
	return this.getClass().toString().substring(6) + "." + toString();
    }
}
