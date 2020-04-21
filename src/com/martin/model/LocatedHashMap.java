package com.martin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Thank you to Stack Overflow for this class
 * Found in question : What happens when a duplicate key is put into a HashMap.
 */
/**
 * This class is such as a HashMap who allows the user to put multiple
 * values with the same id.
 * 
 * @param <K> the type of the keys.
 * @param <V> the type of the values
 */
public class LocatedHashMap<K, V> {
	/*
	 * The map with the type parameters
	 */
	private Map<K, ArrayList<V>> map = new HashMap<>();

	/**
	 * Put in the map the value v where the key k. Creates a new key if
	 * necessary.
	 * 
	 * @param k the key
	 * @param v the value to put
	 */
	public void put(K k, V v) {
		if (map.containsKey(k)) {
			map.get(k).add(v);
		} else {
			final ArrayList<V> array = new ArrayList<>();
			array.add(v);
			map.put(k, array);
		}
	}

	/**
	 * Returns an ArrayList of all the elements with the given key
	 * 
	 * @param k the key
	 * @return an ArrayList of all the elements with the given key
	 */
	public ArrayList<V> get(K k) {
		return map.get(k) == null ? new ArrayList<V>() : map.get(k);
	}

	/**
	 * Returns an object with the given key and the given index. Might
	 * return null if the list (for the given key) doesn't contains this
	 * range.
	 * 
	 * @param k
	 * @param index
	 * @return
	 */
	public V get(K k, int index) throws IndexOutOfBoundsException {

		return map.get(k).size() - 1 < index ? null : map.get(k).get(index);
	}
}
