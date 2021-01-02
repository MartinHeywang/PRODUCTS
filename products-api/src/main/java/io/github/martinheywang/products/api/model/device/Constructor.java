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
package io.github.martinheywang.products.api.model.device;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.Recipe;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.resource.DefaultResource;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.ResourceManager;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.persistance.Request;
import io.github.martinheywang.products.api.utils.PackUtils;
import io.github.martinheywang.products.api.utils.ResourceUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

/**
 * <p>
 * A constructor is a complex
 * {@link io.github.martinheywang.products.api.model.device.Device} that takes
 * multiple entries but one exit, and create valuable products based on
 * different resources.
 * </p>
 * <p>
 * It checks whether a resource is craftable or not by searching an
 * {@link io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup}
 * targeting the keyword "recipe". This AnnotationPackGroup represents the
 * recipe/the craft of the resource.
 * </p>
 */
@Buildable
@ActionCost("50")
@AccessibleName("Constructeur")
@Description("Crée des produits à partir de ressources primaires ou d'autres produits.")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.EXIT, left = PointerType.ENTRY)
@Prices(build = "20000", upgradeTo2 = "75000", upgradeTo3 = "300000", destroyAt1 = "17500", destroyAt2 = "60000", destroyAt3 = "275000")
public final class Constructor extends Device {

	private static final List<Resource> acceptedResources = new ArrayList<>();
	private final List<Resource> availableResources = new ArrayList<>();
	private final List<Resource> extractedRecipe = new ArrayList<>();

	private Pack product;

	/**
	 * Creates a new Constructor with default values.
	 * 
	 * @param model a device model, where the type may be null.
	 */
	public Constructor(final DeviceModel model) {
		super(model);

		if (acceptedResources.isEmpty()) {
			// Iterating over all the resources
			for (Resource resource : ResourceManager.getReferences()) {
				if (ResourceUtils.hasGroup(resource, "recipe"))
					acceptedResources.add(resource);
			}
		}
		this.loadProduct();
	}

	private void loadProduct() {
		if (this.model.get().getPacks().size() > 0) {
			this.product = Iterables.get(this.model.get().getPacks(), 0);
		} else {
			this.product = new Pack(DefaultResource.NONE, BigInteger.ZERO, model.get());
			Request.getSingleton().create(this.product);
		}
		this.generateExtractedRecipe();
	}

	private void generateExtractedRecipe() {
		this.extractedRecipe.clear();
		final Resource resource = this.product.getResource();
		if (!ResourceUtils.hasGroup(resource, "recipe"))
			return;

		final Recipe recipe = PackUtils.toRecipe(ResourceUtils.getGroup(resource, "recipe"));

		this.extractedRecipe.addAll(recipe.extract());
	}

	@Override
	public Action act(final Pack resource) throws MoneyException {
		final Action action = new Action(this, resource);

		this.addResources(resource);
		action.addCost(this.getActionCost());
		action.markAsSuccessful();

		if (this.checkIngredients()) {
			final Coordinate output = this.template.get().getPointersFor(PointerType.EXIT).get(0);
			action.setOutput(output);
			action.setGivenPack(this.product);
		}

		return action;
	}

	/**
	 * Unpack the given packs and add it to the availableResources list.
	 * 
	 * @param packs the packs to 'unpack'
	 */
	private void addResources(final Pack... packs) {
		for (final Pack pack : packs)
			for (BigInteger i = BigInteger.ZERO; i.compareTo(pack.getQuantity()) == -1; i = i.add(BigInteger.ONE))
				this.availableResources.add(pack.getResource());
		for (int i = 0; this.availableResources.size() > 30; i++)
			this.availableResources.remove(i);
	}

	/**
	 * THis method act as a modified 'containsAll()'. It checks if the storage
	 * contains each of the resources that are needed to create the product.
	 * 
	 * @return true - if the resources are available
	 */
	private boolean checkIngredients() {
		if (this.extractedRecipe.isEmpty())
			// This case should happen only if the resource is either NONE, or
			// not craftable
			return false;

		final List<Resource> tempo = new ArrayList<>();
		for (final Resource resource : this.extractedRecipe)
			if (this.availableResources.contains(resource)) {
				tempo.add(resource);
				this.availableResources.remove(resource);
			} else {
				this.availableResources.addAll(tempo);
				tempo.clear();
				return false;
			}
		return true;
	}

	@Override
	public void saveElements() {
		Request.getSingleton().update(this.product);
	}

	/**
	 * Returns the product of the constructor.
	 * 
	 * @return the resource
	 */
	public Resource getProduct() {
		return this.product.getResource();
	}

	/**
	 * Sets a new distributed resource to the device. <strong>Warning: If this
	 * resource is not a part of the accepted resources, this method won't warn you
	 * but the constructor will skip its action as it can't distributed it.</strong>
	 * 
	 * @param res the new resource
	 */
	public void setProduct(final Resource res) {
		this.product.setResource(res);
		this.generateExtractedRecipe();
	}

	/**
	 * Removes an accepted resource, if it is in the list
	 * 
	 * @param res the res to remove
	 */
	public static final void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}

}
