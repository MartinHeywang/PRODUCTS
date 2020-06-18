package com.martinheywang.model;

import java.text.NumberFormat;
import java.util.Locale;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@DatabaseTable(tableName = "packs")
public class Pack implements Displayable<Pack> {

	@DatabaseField(columnName = "id", generatedId = true)
	private Long idPaquet;

	@DatabaseField
	private Resource resource;

	@DatabaseField
	private int quantity;

	@DatabaseField(columnName = "device", foreign = true, foreignColumnName = "id")
	private DeviceModel model;

	public Pack() {
	}

	public Pack(Resource resource, int quantity) {
		this.resource = resource;
		this.quantity = quantity;
	}

	public Pack(Resource resource, int quantity, DeviceModel model) {
		this.resource = resource;
		this.quantity = quantity;
		this.model = model;
	}

	@Override
	public Displayer<Pack> getDisplayer() {
		// Root for the displayer (the element that is nested in the displayer
		VBox root = new VBox();
		// Name of the resource
		Label name = new Label(this.getRessource().getName());
		// If the text is too long, reduce its size
		if (name.getText().length() > 15)
			name.setFont(new Font(10d));
		// View of the resource
		ImageView view = new ImageView(new Image(
				getClass().getResourceAsStream(this.getRessource().getURL())));
		// And how many there is this pack
		Label quantity = new Label(String.valueOf(NumberFormat
				.getInstance(Locale.getDefault()).format(getQuantity())));
		// Add all elements in the root
		root.getChildren().addAll(name, view, quantity);
		// And nest the root in the displayer
		return new Displayer<Pack>(root, this);
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
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
				+ resource + ". Quantity : " + quantity + ".";
	}
}
