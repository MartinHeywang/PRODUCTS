package com.martinheywang.model;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.model.resources.Craftable.RemotePack;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@DatabaseTable
public class Pack implements Displayable<Pack> {

    @DatabaseField(columnName = "id", generatedId = true)
    private Long idPaquet;

    @DatabaseField
    private Resource resource;

    @DatabaseField
    private BigInteger quantity;

    public Pack() {
    }

    public Pack(Resource resource, BigInteger quantity) {
	this.resource = resource;
	this.quantity = quantity;
    }

    @Override
    public Displayer<Pack> getDisplayer() {

	final VBox root = new VBox();
	root.setAlignment(Pos.CENTER);
	root.setPadding(new Insets(3));

	final Label name = new Label(this.getResource().getName());

	if (name.getText().length() > 15)
	    name.setFont(new Font(10d));

	final ImageView view = new ImageView(new Image(getClass().getResourceAsStream(this.getResource().getURL())));
	final Label quantity = new Label(
		String.valueOf(NumberFormat.getInstance(Locale.getDefault()).format(getQuantity())));

	root.getChildren().addAll(name, view, quantity);

	return new Displayer<Pack>(root, this);
    }

    /**
     * Transforms an array of remote packs into an array of regular packs.
     * 
     * @param packs the remote packs
     * @return the regular packs
     */
    public static Pack[] toPack(RemotePack... packs) {
	final Pack[] realPacks = new Pack[packs.length];

	for (int i = 0; i < packs.length; i++) {
	    realPacks[i] = toPack(packs[i]);
	}

	return realPacks;
    }

    /**
     * Transform a RemotePack in a regular pack.
     * 
     * @param pack
     * @return
     */
    public static Pack toPack(RemotePack pack) {
	return new Pack(Resource.valueOf(pack.clazz(), pack.field()), new BigInteger(pack.quantity()));
    }

    /**
     * 
     * @return the id
     */
    public Long getID() {
	return idPaquet;
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
	return resource;
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
	return quantity;
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
	this.quantity = this.quantity.add(quantity);
    }

    @Override
    public String toString() {
	return resource + " x" + quantity;
    }
}
