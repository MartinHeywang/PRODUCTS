package com.martinheywang.model.resources;

import java.text.NumberFormat;
import java.util.ArrayList;
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
 * override. <br>
 * It manages also all the Resource in the game, with it List. You can
 * directly found this list by calling
 * {@link Resource#getReferences()}, and add a Resource that your
 * plugin provides by calling
 * {@link Resource#addReferenceResource(Resource)}.
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
	List<Resource> resourceReferences = new ArrayList<Resource>();

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
	public long getPrice();

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

	/**
	 * Adds the specified Resource in the list of all the game. This
	 * method is protected againts adding twice the same Resource. But for
	 * safety measures, it is good manner to call this method only in the
	 * constructor of the enum where you specified your additional
	 * resources.
	 * 
	 * @param resource the resource to add
	 */
	public static void addReferenceResource(Resource resource) {
		if (!resourceReferences.contains(resource)) {
			resourceReferences.add(resource);
		} else {
			System.out.println(
					"WARNING: This resource was already added in the list.");
		}
	}

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
		return resourceReferences;
	}

	public static Resource valueOf(String inputString) {
		// Todo : search through all enums in all plugins
		// For the time being, I olny search through the BaseResource class
		return BaseResources.valueOf(inputString);
	}
}
