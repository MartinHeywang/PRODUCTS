package com.martin.model.appareils.comportement;

import java.sql.SQLException;
import java.util.List;

import com.martin.Database;
import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.DeviceModel;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Buyer_ implements Behaviour {
	private Level level;
	private JeuContrôle controller;

	private Packing distributedResource;

	public Buyer_(DeviceModel model, JeuContrôle controller) {
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
					throw new MoneyException("Le comportement d'un "
							+ "acheteur n'a pas pu être réalisé car le solde "
							+ "d'argent n'était pas assez important.");
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
	public void setDistributedResource(Packing distributedResource) {
		switch (distributedResource.getRessource()) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			this.distributedResource = distributedResource;
			try {
				Database.daoPacking().update(distributedResource);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		default:
			break;
		}

	}

	/**
	 * 
	 * @return resDistribuée the distributed resource
	 */
	public Packing getDistributedResource() {
		return distributedResource;
	}
}