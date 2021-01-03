
/*
   Copyright 2021 Martin Heywang

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

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.properties.SimpleBigIntegerProperty;
import io.github.martinheywang.products.api.model.properties.SimpleResourceProperty;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.persistance.Persistable;

import java.math.BigInteger;

/**
 * <p>
 * A Pack is a simple object that result in the combination of a
 * {@link Resource} and {@link BigInteger} quantity. It basically represents a
 * box where we can put as many resources as we want.
 * </p>
 * <p>
 * This combination is used for the most part by devices : imagine that each
 * device opens the pack, do whatever it does, and package it to send it to the
 * next device.
 * </p>
 * <p>
 * If it doesn't take place in the assembly line, it may be used by the devices
 * to store a resource. As example, the buyer needs to save the distributed
 * resource to find it the next time it loads. This time the {@link #getModel()}
 * will not return null, as it has a connection with a device model (Note: the
 * device model is the part of the device that is persistant)
 * </p>
 * 
 * @author Martin Heywang
 */
public final class Pack implements Persistable {
	
	private long id;
	private SimpleResourceProperty resource;
	private SimpleBigIntegerProperty quantity;
	private DeviceModel model;

	/**
	 * Creates a new empty Pack. This is used by the ORM when creating the object.
	 * Unless you inject values into the field, you won't be able to use the object
	 * properly.
	 */
	public Pack() {
	}

	/**
	 * Creates a new Pack using the given values.
	 * 
	 * @param resource the resource of the pack
	 * @param quantity the quantity of the pack
	 */
	public Pack(Resource resource, BigInteger quantity) {
		this.resource = new SimpleResourceProperty(resource);
		this.quantity = new SimpleBigIntegerProperty(quantity);
	}

	/**
	 * Creates a new Pack using the given values, associating a model with it.
	 * 
	 * @param resource the resource of the pack
	 * @param quantity the quantity of the pack
	 * @param model    the associated model.
	 */
	public Pack(Resource resource, BigInteger quantity, DeviceModel model) {
		this.resource = new SimpleResourceProperty(resource);
		this.quantity = new SimpleBigIntegerProperty(quantity);
		this.model = model;
	}

	@Override
	public long getID() {
		return this.id;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	/**
	 * The resource of the pack is the resource contained in the pack.
	 * 
	 * @return the resource property
	 */
	public SimpleResourceProperty resourceProperty() {
		return resource;
	}

	/**
	 * 
	 * @return the resource
	 */
	public Resource getResource() {
		return this.resource.get();
	}

	/**
	 * 
	 * @param res the resource to set
	 */
	public void setResource(Resource res) {
		this.resource.set(res);
	}

	/**
	 * The quantity is the count of resource in the same pack.
	 * 
	 * @return the quantity property
	 */
	public SimpleBigIntegerProperty quantityProperty() {
		return this.quantity;
	}

	/**
	 * 
	 * @return the quantity
	 */
	public BigInteger getQuantity() {
		return this.quantity.get();
	}

	/**
	 * 
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigInteger quantity) {
		this.quantity = new SimpleBigIntegerProperty(this.quantity.get().add(quantity));
	}

	/**
	 * 
	 * @param quantity the quantity to add
	 */
	public void addQuantity(BigInteger quantity) {
		this.setQuantity(this.getQuantity().add(quantity));
	}

	/**
	 * Merges this pack with the other. Doesn't apply any changes to this object. If
	 * both packs has the same resource, returns a pack that has the sum of the
	 * quantity.
	 * 
	 * @param other the pack to merge with
	 * @return the newly created pack, result of both packs (this and other)
	 */
	public Pack merge(Pack other) {
		if (this.getResource() != other.getResource())
			return null;

		return new Pack(this.getResource(), this.getQuantity().add(other.getQuantity()));
	}

	/**
	 * @return the associated model, if it has one.
	 */
	public DeviceModel getModel() {
		return this.model;
	}

	/**
	 * Sets the associated model.
	 * 
	 * @param model the new model
	 */
	public void setModel(DeviceModel model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return this.resource + " x" + this.quantity;
	}
}
