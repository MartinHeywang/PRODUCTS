package io.github.martinheywang.products.api.model;

import java.math.BigInteger;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.resource.Resource;

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

	public Pack() {
	}

	public Pack(Resource resource, BigInteger quantity) {
		this.resource = resource;
		this.quantity = quantity;
	}

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

	public Pack merge(Pack other) {
		if (this.getResource() != other.getResource())
			return null;

		return new Pack(this.getResource(),
				this.getQuantity().add(other.getQuantity()));
	}

	public DeviceModel getModel() {
		return this.model;
	}

	public void setModel(DeviceModel model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return this.resource + " x" + this.quantity;
	}
}
