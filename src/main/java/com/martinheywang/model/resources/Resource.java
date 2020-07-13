package com.martinheywang.model.resources;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.martinheywang.model.Pack;
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
	HashMap<String, Resource> references = new HashMap<>();

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

	/**
	 * Returns the recipe used to create this product. May be null.
	 * 
	 * @throws NullPointerException in case the resource is not craftable
	 * @return a list of resources
	 */
	public List<Pack> getRecipe();

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
	public static Collection<Resource> getReferences() {
		return references.values();
	}

	/**
	 * Returns a Resource from the given input String. Those are examples
	 * of valid input String :
	 * <ul>
	 * <li>BaseResources.NONE -> NONE</li>
	 * <li>BaseResources.CIRCUIT -> CIRCUIT</li>
	 * </ul>
	 * 
	 * @param inputString a valid input
	 * @return a Resource from the given input, or null if none is found.
	 */
	public static Resource valueOf(String inputString) {
		return references.get(inputString);
	}

	/**
	 * Register a new Resource in the list of references. This method is
	 * protected against adding twice the same type.
	 * 
	 * @param resource the resource to add
	 */
	public static void addReferences(Resource resource) {
		references.put(resource.toString(), resource);
	}

	/**
	 * Unregister the given resource in the list of references.
	 * 
	 * @param resource the resource to remove
	 */
	public static void removeReferences(Resource resource) {
		references.remove(resource.toString());
	}
}
