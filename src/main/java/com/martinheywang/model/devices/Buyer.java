package com.martinheywang.model.devices;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.ActionCost;
import com.martinheywang.model.devices.annotations.Buildable;
import com.martinheywang.model.devices.annotations.DefaultTemplate;
import com.martinheywang.model.devices.annotations.Description;
import com.martinheywang.model.devices.annotations.Prices;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.Buyable;
import com.martinheywang.model.resources.DefaultResource;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.components.Carousel;

import javafx.scene.Node;

@Buildable
@ActionCost("5")
@AccessibleName("Acheteur")
@Description("Il achète les ressources et les envoit au prochain appareil.")
@DefaultTemplate(bottom = PointerTypes.EXIT)
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
     * All the Resource must be marked with the {@link Buyable}
     * annotation, but all Resources marked with Buyable <em>might be</em>
     * in it.
     * </p>
     */
    private static final List<Resource> acceptedResources = new ArrayList<>();

    private final Pack distributedResource;

    public Buyer(DeviceModel model) {
	super(model);

	// Buyers auto-activates
	Device.autoActiveDevices.add(this);

	// Todo: fetch the distributed pack
	this.distributedResource = new Pack(DefaultResource.NONE, new BigInteger("1"));
    }

    @Override
    public void act(Pack resource) throws MoneyException {

	// Check if the resource is valid one (if the resource is buyable).
	if (!acceptedResources.contains(this.distributedResource.getResource())) {
	    return;
	}

	// Here we retreive the price of the resource.
	final BigInteger resourceCost = new BigInteger(
		this.distributedResource.getResource()
		.getField().getAnnotation(Buyable.class).price());

	// Here we check if we actually have enough money
	if (this.gameManager.getMoney()
		.compareTo(resourceCost.add(this.getActionCost())) == -1) {
	    // We don't have enough money (:sad-guy:)
	    throw new MoneyException();
	}

	final Coordinate output = this.template
		.getPointersFor(PointerTypes.EXIT).get(0);

	if (this.gameManager.connectionExists(this.getPosition(), output)) {
	    // Remove action cost
	    this.gameManager.removeMoney(this.getActionCost().add(resourceCost));

	    this.gameManager.performAction(this.getPosition(), output, this.distributedResource);
	    this.setActive(true);
	    this.setActive(false);
	}
    }

    @Override
    public List<Node> getWidgets() {
	final Carousel carousel = new Carousel();
	Node selection = null;

	carousel.addNodes(DefaultResource.NONE.getDisplayer());
	for (final Resource resource : acceptedResources) {
	    final Displayer<Resource> display = resource.getDisplayer();
	    carousel.addNodes(display);

	    if (distributedResource.getResource() == resource) {
		selection = display;
	    }
	}
	carousel.setSelection(selection);

	carousel.setOnSelectionChanged(event -> {
	    @SuppressWarnings("unchecked")
	    final Resource resource = ((Displayer<Resource>) event.getNewSelection()).getSubject();
	    this.setDistributedResource(resource);
	});

	return Arrays.asList(carousel);
    }

    /**
     * Returns the distributed resource of the buyer.
     * 
     * @return the resource
     */
    public Resource getDistributedResource() {
	return distributedResource.getResource();
    }

    /**
     * Sets a new distributed resource to the device. <strong>Warning: If this
     * resource is not a part of the accepted resources, this method won't warn you
     * but the buyer will skip its action as it can't distributed it.</strong>
     * 
     * @param res the new resource
     */
    public void setDistributedResource(Resource res) {
	distributedResource.setResource(res);
    }

    /**
     * Adds a Resource to the accepted resources. Note that it must be marked with
     * the Buyable annotation.
     * 
     * @param res the res to add
     */
    public static final void addAcceptedResource(Resource res) {
	if (res.hasAnnotation(Buyable.class)) {
	    acceptedResources.add(res);
	}
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
