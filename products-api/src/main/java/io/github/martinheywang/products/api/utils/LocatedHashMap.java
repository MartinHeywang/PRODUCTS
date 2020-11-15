/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	 * Replace in the map at the id with a new ArrayList with one element,
	 * the value given as parameter.
	 * 
	 * @param k the key (where to replace)
	 * @param v the value to add (what replaces the list)
	 */
	public void set(K k, V v) {
		final ArrayList<V> array = new ArrayList<>();
		array.add(v);
		map.put(k, array);
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
	 * @param k the key
	 * @param index the index in the list.
	 * @return the value at the given key and index.
	 */
	public V get(K k, int index) throws IndexOutOfBoundsException {

		return map.get(k).size() - 1 < index ? null : map.get(k).get(index);
	}

	/**
	 * Returns the size of the map (the number of lists in the map)
	 *
	 * @return the size of the map
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Returns true if the given key already exists in the table.
	 * 
	 * @param key the key to check
	 * @return whether the given key is contained in the map.
	 */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	/**
	 * 
	 * @return all the registered keys
	 */
	public Set<K> keySet() {
		return map.keySet();
	}

	/**
	 * Empties the map and delete all the keys and all the values.
	 */
	public void clearAll() {
		map.clear();
	}
}
