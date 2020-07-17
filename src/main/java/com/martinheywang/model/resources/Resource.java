package com.martinheywang.model.resources;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
 * {@link Resource#getReferences()}, and add a Resource that your
 * plugin provides by calling
 * {@link Resource#addReferenceResource(Resource)}.
 * </p>
 * <p>
 * But this action does not mean that every devices will be able to
 * use it. To make a resource acceptable by a specific device, such as
 * buyers, you will need to use the method of the behaviour of this
 * device. But be careful, some devices may have some additionnal
 * rules.
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
	 * Returns a String representing a relative path to the view
	 * representing this resource.
	 * 
	 * @return
	 */
	public String getURL();

	@Override
	public default Displayer<Resource> getDisplayer() {

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER_LEFT);
		root.setPadding(new Insets(3));

		Label nom = new Label();
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(this.getName());
		if (nom.getText().length() > 15)
			nom.setFont(new Font(10d));
		nom.setPrefHeight(20d);
		root.getChildren().add(nom);

		ImageView image = new ImageView();
		image.setImage(
				new Image(getClass().getResourceAsStream(this.getURL())));
		root.getChildren().add(image);

		Label infos = new Label();
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
	 * Returns a Resource from the given input String. It must his full
	 * name, for example:
	 * <code>you.username.plugin.YourClass.NAME_OF_THE_FIELD</code>
	 * 
	 * <strong>NOTE: the path must be a dotted one (as shown in the
	 * example)</strong>
	 * 
	 * @param input a valid input
	 * @return a Resource from the given input, or null if none is found.
	 */
	public static Resource valueOf(String input) {
		final int lastDot = input.lastIndexOf('.');
		final String classFullName = input.substring(0, lastDot);
		final String fieldName = input.substring(lastDot + 1);
		final Class<?> cl;
		try {
			cl = Class.forName(classFullName);

			final Resource res = (Resource) cl.getDeclaredField(fieldName)
					.get(null);
			if (!references.contains(res)) {
				System.err.println(
						"WARNING: The requested resource has been found, "
								+ "but is not registered in the references.");
			}
			return res;
		} catch (ClassNotFoundException e) {
			System.err.println("The requested class has not be found.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			System.err.println(
					"The class has been found, but the requested field doesn't exist.");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
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

		for (Field field : clazz.getFields()) {
			try {
				if (Resource.class.isAssignableFrom(field.getType())) {
					addReferences((Resource) field.get(null));
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
		for (Resource res : resource) {
			references.add(res);
		}
	}

	/**
	 * Unregister the given resource in the list of references.
	 * 
	 * @param resource the resource to remove
	 */
	public static void removeReferences(Resource... resource) {
		for (Resource res : resource) {
			references.remove(res);
		}
	}
}
