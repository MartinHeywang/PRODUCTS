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
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Independent;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.resource.DefaultResource;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.ResourceManager;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.persistance.Packs;
import io.github.martinheywang.products.api.utils.ResourceUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;

/**
 * <p>
 * The buyer is an independent
 * {@link io.github.martinheywang.products.api.model.device.Device} that take
 * nothing as entry and buys the resource to distribute it.
 * </p>
 * <p>
 * To know whether a resource if buyable or not, it checks fro a tag having the
 * property "buyable" and the value "true". The cost of it is defined at 10 for
 * the moment.
 * </p>
 */
@Buildable
@Independent
@ActionCost("10")
@AccessibleName("Acheteur")
@Description("Il achète les ressources et les envoit au prochain appareil.")
@DefaultTemplate(bottom = PointerType.EXIT)
@Prices(build = "500", destroyAt1 = "450", destroyAt2 = "9000", destroyAt3 = "140000", upgradeTo2 = "10000", upgradeTo3 = "150000")
public final class Buyer extends Device {

	/**
	 * <p>
	 * The list of accepted resources of this device.
	 * </p>
	 * <p>
	 * To add a Resource to this list you can either invoke
	 * {@link #addAcceptedResource(Resource)} or mark the declaration with
	 * {@link Buyable}.
	 * </p>
	 * <p>
	 * All the Resource must be marked with the {@link Buyable} annotation, but all
	 * Resources marked with Buyable <em>might be</em> in it.
	 * </p>
	 */
	private static final List<Resource> buyableResources = new ArrayList<>();

	private Pack distributedResource;
	private BigInteger resourceCost;

	/**
	 * Creates a new buyer with the given model.
	 * 
	 * @param model a device model, where the type may be null.
	 */
	public Buyer(DeviceModel model) {
		super(model);
		
		if (buyableResources.isEmpty()) {
			// Iterating over all the resources
			for (Resource resource : ResourceManager.getReferences()) {
				if (ResourceUtils.getPropertyValue(resource, "buyable").equals("true"))
				buyableResources.add(resource);
			}
		}
		
		this.loadDistributedResource();
	}

	/**
	 * Loads the distributed resource when the device is created
	 */
	private void loadDistributedResource() {
		if(this.model.get().getPacks().size() > 0){
			this.distributedResource = Iterables.get(this.model.get().getPacks(), 0);
		}else{
			this.distributedResource = new Pack(DefaultResource.NONE, BigInteger.ZERO, model.get());
			Packs.getSingleton().create(this.distributedResource);
		}
	}

	@Override
	public Action act(Pack resource) throws MoneyException {
		final Action action = new Action(this, null);

		if (!buyableResources.contains(this.distributedResource.getResource()))
			// Return early; the distributed resource isn't a valid one.
			return action;

		final Coordinate output = this.template.get().getPointersFor(PointerType.EXIT).get(0);

		action.addCost(this.getActionCost().add(this.resourceCost));
		action.setGivenPack(new Pack(this.distributedResource.getResource(), BigInteger.ONE));
		action.setOutput(output);
		action.markAsSuccessful();
		return action;
	}

	@Override
	public void saveElements() {
		Packs.getSingleton().update(this.distributedResource);
	}

	/**
	 * Returns the distributed resource of the buyer.
	 * 
	 * @return the resource
	 */
	public Resource getDistributedResource() {
		return this.distributedResource.getResource();
	}

	/**
	 * Sets a new distributed resource to the device. <strong>Warning: If this
	 * resource is not a part of the accepted resources, this method won't warn you
	 * but the buyer will skip its action as it can't distribute it.</strong>
	 * 
	 * @param res the new resource
	 */
	public void setDistributedResource(Resource res) {
		if (!buyableResources.contains(res))
			// Return early; the given resource isn't distributable.
			return;

		this.distributedResource.setResource(res);
		this.distributedResource.setQuantity(BigInteger.ONE);
		this.resourceCost = new BigInteger("10");
	}
}