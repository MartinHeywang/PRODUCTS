package com.martinheywang.model.types;

import java.math.BigInteger;
import java.util.HashMap;

import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Template.TemplateModel;

/**
 * Interface that defines a Type. Should be implemented by an enum who
 * references the available types. {@link BaseTypes} is an example on
 * how to do it.
 * 
 * @author Heywang
 *
 */
public interface Type {

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
	 * Returns a HashMap of prices, with on the first side as a String
	 * (the id of the value), and on the other side a BigInteger, the
	 * value itself.<br>
	 * You must give a price for the given id's :
	 * <ul>
	 * <li>"level_1_build", how many you lost on building this device</li>
	 * <li>"level_2_build", how many you lost on upgrading to level 2</li>
	 * <li>"level_3_build", how many you lost on upgrading to level 3</li>
	 * <li>"level_1_delete", how many you earn on deleting this device on
	 * level 1</li>
	 * <li>"level_2_delete", how many you earn on deleting this device on
	 * level 2</li>
	 * <li>"level_3_delete", how many you earn on deleting this device on
	 * level 3</li>
	 * </ul>
	 * 
	 * If other id's are given, those will be ignored.
	 * 
	 * @return a map of prices.
	 */
	public HashMap<String, BigInteger> getPrices();

	/**
	 * Returns the price for the given id. More information about id's
	 * price {@link Type#getPrices here}.
	 * 
	 * @param key the id
	 * @return the value corresponding to the key, or null if no value is
	 *         found
	 */
	public default BigInteger getPrice(String key) {
		return getPrices().get(key);
	}

	/**
	 * The class that extends Device that manages this Device. More
	 * informations in extending Device class can be found {@link Device
	 * here}.
	 * 
	 * @return
	 */
	public Class<? extends Device> getAssociatedClass();

	public TemplateModel getTemplateModel();

	public static Type valueOf(String inputString) {
		// Todo : search through all enums in all plugins
		// For the time being, I only search through the BaseResource class
		return BaseTypes.valueOf(inputString);
	}
}
