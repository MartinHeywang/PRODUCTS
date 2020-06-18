package com.martinheywang.model.types;

import java.math.BigInteger;

import com.martinheywang.model.devices.Device;

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
	 * The price of the device, in other words how many it costs at build
	 * time.
	 * 
	 * @return the price
	 */
	public BigInteger getPrice();

	/**
	 * The class that extends Device that manages this Device. More
	 * informations in extending Device class can be found {@link Device
	 * here}.
	 * 
	 * @return
	 */
	public Class<? extends Device> getAssociatedClass();

	public static Type valueOf(String inputString) {
		// Todo : search through all enums in all plugins
		// For the time being, I only search through the BaseResource class
		return BaseTypes.valueOf(inputString);
	}
}
