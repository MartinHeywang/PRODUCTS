package com.martinheywang.model.devices.behaviours;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.Resource;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Buyer_ implements Behaviour {

	@SuppressWarnings("serial")
	public static List<Resource> acceptedResources = new ArrayList<Resource>() {
		{
			add(BaseResources.NONE);
			add(BaseResources.FER);
			add(BaseResources.OR);
			add(BaseResources.CUIVRE);
			add(BaseResources.ARGENT);
			add(BaseResources.DIAMANT);
			add(BaseResources.ALUMINIUM);
		}
	};

	private Level level;
	private GameController controller;

	private Pack distributedResource;

	public Buyer_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;

		// Default definition to avoid null values
		distributedResource = new Pack(BaseResources.NONE, 1, model);

		try {
			// Query for all the packages that are associated to this device
			final List<Pack> list = Database.daoPacking().queryBuilder()
					.where().eq("device", model.getIdAppareilModel()).query();
			// If its size equals 0, then create the resource and save it in the
			// database
			if (list.size() == 0) {
				distributedResource = new Pack(BaseResources.NONE, 1, model);
				Database.daoPacking().create(distributedResource);
			}
			// Else we get at the first index the packing
			else {
				distributedResource = list.get(0);
			}

			// If the list is bigger than 1, there is an error (the resource was
			// added by the user (not in game)).
			// So the rest just doesn't matter
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());

		}
	}

	@Override
	public void action(Pack resATraiter, Coordinates pointer)
			throws MoneyException {

		distributedResource.setQuantity(0);

		for (int niveau = 0; niveau < this.level.getNiveau(); niveau++) {

			if (controller.getMoney() < 5
					+ Device.getElectricity())
				throw new MoneyException();
			else {
				if (!distributedResource.getRessource()
						.equals(BaseResources.NONE)) {
					distributedResource.addQuantity(1);
					controller.removeMoney(5 + Device.getElectricity());
					controller.findDevice(pointer).action(distributedResource);
				}
			}
		}

	}

	/**
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * 
	 * @param produit the resource to set
	 */
	public void setDistributedResource(Pack pack) {
		if (acceptedResources.contains(pack.getRessource())) {
			this.distributedResource.setRessource(pack.getRessource());

			this.distributedResource.setQuantity(pack.getQuantity());
			try {
				Database.daoPacking().update(distributedResource);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @return distributedResource the distributed resource
	 */
	public Pack getDistributedResource() {
		return distributedResource;
	}

	public static void addAcceptedResource(Resource res) {
		acceptedResources.add(res);
	}

	public static void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}
}