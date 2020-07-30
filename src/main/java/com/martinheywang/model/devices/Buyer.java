package com.martinheywang.model.devices;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.martinheywang.model.resources.Resource;
import com.martinheywang.model.templates.Template.PointerTypes;

@Buildable
@ActionCost("5")
@AccessibleName("Acheteur")
@Description("Il achète les ressources et les envoit au prochain appareil.")
@DefaultTemplate(bottom = PointerTypes.EXIT)
@Prices(build = "500", destroyAt1 = "450", destroyAt2 = "9000", destroyAt3 = "140000", upgradeTo2 = "10000", upgradeTo3 = "150000")
public final class Buyer extends Device {

	private Pack distributedResource;

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

	public Buyer(DeviceModel model) {
		super(model);
	}

	@Override
	public void act(Pack resource) throws MoneyException {
		// Check if the resource is valid one (if the resource is buyable).
		if (!acceptedResources.contains(resource.getResource())) {
			return;
		}

		// Here we retreive the price of the resource.
		final BigInteger resourceCost = new BigInteger(
				distributedResource.getResource()
						.getField().getAnnotation(Buyable.class).price());

		// Here we check if we actually have enough money
		if (gameManager.getMoney()
				.compareTo(resourceCost.add(getActionCost())) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}

		final Coordinate output = this.template
				.getPointersFor(PointerTypes.ENTRY).get(0);

		// Remove action cost
		gameManager.removeMoney(getActionCost().add(resourceCost));

		gameManager.performAction(getPosition(), output,
				distributedResource);
	}

	/**
	 * Adds a Resource to the accepted resources. Note that it must be
	 * marked with the Buyable annotation.
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
