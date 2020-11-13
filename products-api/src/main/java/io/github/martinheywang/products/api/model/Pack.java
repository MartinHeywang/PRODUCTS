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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * <p>A Pack is the combination between a Resource and a positive BigInteger quantity.</p>
 * <p>Instances of this class may be persistent, as it is marked with {@link DatabaseTable}</p>
 */
@DatabaseTable
public class Pack {

	@DatabaseField(columnName = "id", generatedId = true)
	private Long idPaquet;

	@DatabaseField
	private Resource resource;

	@DatabaseField
	private BigInteger quantity;

	@DatabaseField(columnName = "model", foreign = true, foreignColumnName = "id")
	private DeviceModel model;

	/**
	 * Creates a new empty Pack.
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
		this.resource = resource;
		this.quantity = quantity;
	}

	/**
	 * Creates a new Pack using the given values, associating a model with it.
	 * 
	 * @param resource the resource of the pack
	 * @param quantity the quantity of the pack
	 * @param model the associated model.
	 */
	public Pack(Resource resource, BigInteger quantity, DeviceModel model) {
		this.resource = resource;
		this.quantity = quantity;
		this.model = model;
	}

	/**
	 * 
	 * @return the id
	 */
	public Long getID() {
		return this.idPaquet;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setID(Long id) {
		this.idPaquet = id;
	}

	/**
	 * 
	 * @return the resource
	 */
	public Resource getResource() {
		return this.resource;
	}

	/**
	 * 
	 * @param res the resource to set
	 */
	public void setResource(Resource res) {
		this.resource = res;
	}

	/**
	 * 
	 * @return the quantity
	 */
	public BigInteger getQuantity() {
		return this.quantity;
	}

	/**
	 * 
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 * @param quantity the quantity to add
	 */
	public void addQuantity(BigInteger quantity) {
		this.setQuantity(this.getQuantity().add(quantity));
	}

	/**
	 * Merges this pack with the other. Doesn't apply any changes to this object.
	 * If both packs has the same resource, returns a pack that has the sum of the quantity.
	 * 
	 * @param other the pack to merge with
	 * @return the newly created pack, result of both packs (this and other)
	 */
	public Pack merge(Pack other) {
		if (this.getResource() != other.getResource())
			return null;

		return new Pack(this.getResource(),
				this.getQuantity().add(other.getQuantity()));
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
