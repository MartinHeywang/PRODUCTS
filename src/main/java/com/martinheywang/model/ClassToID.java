package com.martinheywang.model;

import java.util.HashMap;

/**
 * Utility class that parses class into String and the same String
 * into the same class.
 * 
 * @author Martin Heywang
 */
public final class ClassToID {

	private static final HashMap<String, Class<?>> registeredClasses = new HashMap<>();

	private ClassToID() {

	}

	/**
	 * Register the given class in the list
	 * 
	 * @param clazz the clazz to register
	 */
	public static final void register(Class<?> clazz) {
		registeredClasses.put(classToID(clazz), clazz);
	}

	/**
	 * Transforms the given class into a String.
	 * 
	 * @param clazz the class to crypt
	 * @return the identifier of the class
	 */
	public static final String classToID(Class<?> clazz) {
		return clazz.getCanonicalName();
	}

	/**
	 * Parses the given String into a class. Note that you will need to
	 * register this class before invoking this method elsewhere you will
	 * get a NullPointerException.
	 * 
	 * @param id the id to parse
	 * @return the class, or null if none is found.
	 */
	public static final Class<?> IDToClass(String id) {
		return registeredClasses.get(id);
	}

}
