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
package io.github.martinheywang.products.api.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * A recipe is a list of packs. To define a recipe for a resource, and so make
 * it craftable, you must put the
 * {@link io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup}
 * targeting "recipe". The value of the annotation will be used as the recipe.
 */
public class Recipe {

	/**
	 * The packs of the recipe;
	 */
	private Pack[] packs;

	/**
	 * Creates an empty recipe.
	 */
	public Recipe() {
	}

	/**
	 * Creates a Recipe with the given packs.
	 * 
	 * @param packs the given packs
	 */
	public Recipe(Pack[] packs) {
		this.packs = packs;
	}

	/**
	 * @return the packs
	 */
	public Pack[] getPacks() {
		return packs;
	}

	/**
	 * Set a pack at a given index.
	 * 
	 * @param pack  the pack to set
	 * @param index the index - where to set
	 */
	public void setPack(Pack pack, int index) {
		this.packs[index] = pack;
	}

	/**
	 * Extract this recipe into a list of
	 * {@link io.github.martinheywang.products.api.model.resource.Resource}.
	 * Extracting the recipe means creating a list of resources (not of packs),
	 * getting rid of the quantity. For example, the extracted value for a recipe 2
	 * iron, 1 gold is [iron, iron, gold].
	 * 
	 * @return the extracted recipe
	 */
	public List<Resource> extract() {
		final ArrayList<Resource> list = new ArrayList<>();

		for (Pack pack : this.packs) {
			for (BigInteger i = BigInteger.ZERO; i.compareTo(pack.getQuantity()) == -1; i = i.add(BigInteger.ONE)) {
				list.add(pack.getResource());
			}
		}

		return list;
	}

}
