package com.martinheywang.model.resources;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.martinheywang.model.devices.Buyer;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * 
 * <p>
 * This interface represents a <em>Resource</em> that can be used by
 * all the devices. It extends {@link Displayable}, that does mean
 * that it provides a common displayer for the Resource, that you can
 * override.
 * </p>
 * <p>
 * It manages also all the Resource in the game, with it List. You can
 * directly found this list by calling
 * {@link Resource#getReferences()}.
 * </p>
 * <p>
 * You can register an enum type (extending Resource) by calling
 * {@link #register(Class)}.
 * </p>
 * <p>
 * When registering, make sure to add some of the annotations on your
 * fields provided by the API such as {@link Buyable @Buyable},
 * {@link Craftable @Craftable}, or {@link ToWire @ToWire}. This will
 * add the resources automatically (as long as you register the class)
 * according to the annotations. But still, you can do that manually
 * by calling {@link #addReferences(Resource...)}.
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
public interface Resource extends Displayable<Resource> {

    /**
     * All the resources in the game.
     */
    List<Resource> references = new ArrayList<>();

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
     * Returns a String representing a relative path to the view
     * representing this resource.
     * </p>
     * <p>
     * For example, the following expression :
     * <code>this.getClass().getResourceAsStream(this.getURL())</code>
     * must not be null, where the url is the value returned by the
     * <code>getURL()</code> method, and <code>this.getClass()</code> the
     * class where the valeu is defined.
     * </p>
     * 
     * @return the local uri of the image
     */
    public String getURL();

    @Override
    public default Displayer<Resource> getDisplayer() {

	final VBox root = new VBox();
	root.setAlignment(Pos.CENTER_LEFT);
	root.setPadding(new Insets(3));

	final Label nom = new Label();
	nom.setAlignment(Pos.TOP_CENTER);
	nom.setText(this.getName());
	if (nom.getText().length() > 15)
	    nom.setFont(new Font(10d));
	nom.setPrefHeight(20d);
	root.getChildren().add(nom);

	final ImageView image = new ImageView();
	image.setImage(
		new Image(this.getClass().getResourceAsStream(this.getURL())));
	root.getChildren().add(image);

	final Label infos = new Label();
	infos.setAlignment(Pos.TOP_CENTER);
	infos.setText(
		String.valueOf(NumberFormat.getInstance(Locale.getDefault())
			.format(this.getPrice())) + " €");
	root.getChildren().add(infos);
	return new Displayer<Resource>(root, this);
    }

    /**
     * Gets all of the resources loaded in the game.
     * 
     * @return a list of resources
     */
    public static List<Resource> getReferences() {
	return references;
    }

    /**
     * Returns the value of the
     * 
     * @param clazz
     * @param field
     * @return
     */
    public static Resource valueOf(Class<? extends Resource> clazz,
	    String field) {
	try {
	    final Resource res = (Resource) clazz.getField(field)
		    .get(null);
	    if (!references.contains(res)) {
		System.err.println(
			"WARNING: The requested resource has been found, "
				+ "but is not registered in the references.");
	    }
	    return res;
	} catch (final IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (final IllegalAccessException e) {
	    e.printStackTrace();
	} catch (final NoSuchFieldException e) {
	    System.err.println(
		    "The requested field has not been found (requested: "
			    + field + " in " + clazz.getCanonicalName() + ")");
	    e.printStackTrace();
	} catch (final SecurityException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * <p>
     * Search through all registered resources one matches the given parsable
     * string.
     * </p>
     * <p>
     * This method may be the sources of problems if two resources are named
     * similarly, in this case, the first one found will be returned.
     * </p>
     * <p>
     * If none is found, this method returns the default resource NONE.
     * </p>
     * 
     * @param str a parsable string, containing only the name of the resource.
     */
    public static Resource valueOf(String str) {
	for (final Resource resource : references) {
	    if (resource.toString().equals(str))
		return resource;
	}
	System.err.println("No Resource was found for input string : '" + str + "'\nReturning default resource NONE");
	return DefaultResource.NONE;
    }

    /**
     * Returns true if this Resource object owns the given annotation. It
     * allows you to check if this Resource object is Buyable, for
     * instance.
     * 
     * @param annotation
     * @return
     */
    public default boolean hasAnnotation(
	    Class<? extends Annotation> annotation) {
	try {
	    return this.getClass().getField(toString())
		    .isAnnotationPresent(annotation);
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

    public static void register(Class<? extends Resource> clazz) {
	if (!clazz.isEnum()) {
	    System.out.println(
		    "WARNING: Implementations of Resource must be an enum. Skipping.");
	    return;
	}

	for (final Field field : clazz.getFields()) {
	    try {
		if (Resource.class.isAssignableFrom(field.getType())) {
		    addReferences((Resource) field.get(null));

		    if (field.isAnnotationPresent(Buyable.class)) {
			Buyer.addAcceptedResource((Resource) field.get(null));
		    }
		}
	    } catch (IllegalArgumentException | IllegalAccessException e) {
		e.printStackTrace();
	    }
	}

    }

    /**
     * Register a new Resource in the list of references. This method is
     * protected against adding twice the same type.
     * 
     * @param resource the resource to add
     */
    public static void addReferences(Resource... resource) {
	for (final Resource res : resource) {
	    references.add(res);
	}
    }

    /**
     * Unregister the given resource in the list of references.
     * 
     * @param resource the resource to remove
     */
    public static void removeReferences(Resource... resource) {
	for (final Resource res : resource) {
	    references.remove(res);
	}
    }
}
