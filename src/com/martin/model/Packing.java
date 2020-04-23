package com.martin.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.model.appareils.DeviceModel;

@DatabaseTable(tableName = "packages")
public class Packing {

	@DatabaseField(columnName = "id", generatedId = true)
	private Long idPaquet;

	@DatabaseField
	private Resource resource;

	@DatabaseField
	private int quantity;

	@DatabaseField(columnName = "device", foreign = true, foreignColumnName = "id")
	private DeviceModel model;

	public Packing() {
	}

	public Packing(Resource resource, int quantity) {
		this.resource = resource;
		this.quantity = quantity;
	}

	public Packing(Resource resource, int quantity, DeviceModel model) {
		this.resource = resource;
		this.quantity = quantity;
		this.model = model;
	}

	/**
	 * 
	 * @return the id
	 */
	public Long getIdPaquet() {
		return idPaquet;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setIdPaquet(Long id) {
		this.idPaquet = id;
	}

	/**
	 * 
	 * @return the resource
	 */
	public Resource getRessource() {
		return resource;
	}

	/**
	 * 
	 * @param res the resource to set
	 */
	public void setRessource(Resource res) {
		this.resource = res;
	}

	/**
	 * 
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantité the quantity to set
	 */
	public void setQuantity(int quantité) {
		this.quantity = quantité;
	}

	/**
	 * 
	 * @param quantity the quantity to add
	 */
	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	/**
	 * 
	 * @return the associated-device
	 */
	public DeviceModel getAppareil() {
		return model;
	}

	/**
	 * 
	 * @param appareil the device to set
	 */
	public void setAppareil(DeviceModel appareil) {
		this.model = appareil;
	}

	@Override
	public String toString() {
		return "Object type Paquet. Id : " + idPaquet + ". Ressource : "
				+ resource + ". Quantité : " + quantity + ".";
	}
}
