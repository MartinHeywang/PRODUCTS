package com.martinheywang.model.types;

import java.util.ArrayList;
import java.util.List;

import org.pf4j.ExtensionPoint;

import com.martinheywang.model.behaviours.Behaviour;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.types.info.PricesModule;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Interface that defines a Type. Should be implemented by an enum who
 * references the available types. {@link BaseTypes} is an example on
 * how to do it.<br>
 * To make your type a valid one, you must add it to the list of
 * references using {@link #addReferences(Type)}. This way the game
 * will recognized your type as a valid type and should be able to use
 * it.
 * 
 * @author Martin Heywang
 *
 */
public interface Type extends Displayable<Type>, ExtensionPoint {

	/**
	 * All the resources in the game.
	 */
	List<Type> references = new ArrayList<>();

	/**
	 * The accesible name of the device, shown in the window.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * A String that defines the URL to the images of the device, shown
	 * everywhere.
	 * 
	 * @return a url as a string
	 */
	public String getURL();

	/**
	 * The accesible description of the device, shown in the window.
	 * 
	 * @return the description
	 */
	public String getDescription();

	/**
	 * Returns the PricesModule of this Device's type.
	 * 
	 * @return a map of prices.
	 */
	public PricesModule getPrices();

	/**
	 * Returns the class that extends Device that manages this Device.
	 * More informations in extending Device class can be found
	 * {@link Device here}.
	 * 
	 * @return
	 */
	public Class<? extends Behaviour> getBehaviourClass();

	/**
	 * Returns the set of args that will be given to the behaviour class
	 * given with {@link #getBehaviourClass()}. Learn in the documentation
	 * of the class which args must be given, in which order...etc.
	 * 
	 * @return the set of args.
	 */
	public Object[] getBehaviourArgs();

	public TemplateModel getTemplateModel();

	/**
	 * Returns a Type for the given input String. Here are some examples
	 * of valid inputs :
	 * <ul>
	 * <li>BaseTypes.FLOOR -> FLOOR</li>
	 * <li>BaseTypes.BUYER -> BUYER</li>
	 * </ul>
	 * 
	 * @param inputString the imput
	 * @return the corresponding Type, or null if none is found
	 */
	public static Type valueOf(String inputString) {
		for (Type type : references) {
			if (type.toString().equals(inputString))
				return type;
		}
		return null;
	}

	@Override
	public default Displayer<Type> getDisplayer() {
		final HBox root = new HBox();
		root.setAlignment(Pos.CENTER_LEFT);
		root.setSpacing(20d);
		root.setTranslateX(15d);

		final ImageView view = new ImageView();
		view.setImage(new Image(getClass()
				.getResourceAsStream("/images/devices_level_1/" + getURL())));
		view.setFitHeight(40d);
		view.setFitWidth(40d);

		final Label title = new Label();
		title.setText(getName());

		root.getChildren().addAll(view, title);

		return new Displayer<Type>(root, this);
	}

	/**
	 * Returns all the type registered in the game.
	 * 
	 * @return the references
	 */
	public static List<Type> getReferences() {
		return references;
	}

	/**
	 * Register a new Type in the list of references. This method is
	 * protected against adding twice the same type.
	 * 
	 * @param type the type to add
	 */
	public static void addReferences(Type type) {
		references.add(type);
	}

	/**
	 * Unregister the given type in the list of references.
	 * 
	 * @param type the type to remove
	 */
	public static void removeReferences(Type type) {
		references.remove(type);
	}
}
