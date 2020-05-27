package com.martinheywang.model.devices.behaviours;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.Database;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Packing;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Buyer_ implements Behaviour {

	@SuppressWarnings("serial")
	public static List<Resource> acceptedResources = new ArrayList<Resource>() {
		{
			add(Resource.NONE);
			add(Resource.FER);
			add(Resource.OR);
			add(Resource.CUIVRE);
			add(Resource.ARGENT);
			add(Resource.DIAMANT);
			add(Resource.ALUMINIUM);
		}
	};

	private Level level;
	private GameController controller;

	private Packing distributedResource;

	public Buyer_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;

		// Default definition to avoid null values
		distributedResource = new Packing(Resource.NONE, 1, model);

		try {
			// Query for all the packages that are associated to this device
			final List<Packing> list = Database.daoPacking().queryBuilder()
					.where().eq("device", model.getIdAppareilModel()).query();
			// If its size equals 0, then create the resource and save it in the
			// database
			if (list.size() == 0) {
				distributedResource = new Packing(Resource.NONE, 1, model);
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
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {

		distributedResource.setQuantity(0);

		for (int niveau = 0; niveau < this.level.getNiveau(); niveau++) {

			if (isValid(distributedResource.getRessource())) {
				if (controller.getPartieEnCours().getArgent() < 5
						+ Device.getElectricity())
					throw new MoneyException();
				else {
					distributedResource.addQuantity(1);
					controller.setArgent(5 + Device.getElectricity(), false);
					controller.findDevice(pointer).action(distributedResource);
				}
			}
		}

	}

	private boolean isValid(Resource res) {
		switch (res) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * 
	 * @param produit the resource to set
	 */
	public void setDistributedResource(Packing pack) {
		if (acceptedResources.contains(pack.getRessource())) {
			this.distributedResource = pack;
			try {
				Database.daoPacking().update(pack);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @return distributedResource the distributed resource
	 */
	public Packing getDistributedResource() {
		return distributedResource;
	}

	public static void addAcceptedResource(Resource res) {
		acceptedResources.add(res);
	}

	public static void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}
}