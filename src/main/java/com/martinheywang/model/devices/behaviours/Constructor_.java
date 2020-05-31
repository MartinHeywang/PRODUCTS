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

public class Constructor_ implements Behaviour {

	@SuppressWarnings("serial")
	public static List<Resource> acceptedResources = new ArrayList<Resource>() {
		{
			add(BaseResources.NONE);

			add(BaseResources.CIRCUIT);
			add(BaseResources.PLAQUE_REFROIDISSANTE);
			add(BaseResources.PLAQUE_CHAUFFANTE);
			add(BaseResources.AMPOULE);
			add(BaseResources.HORLOGE);
			add(BaseResources.ANTENNE);
			add(BaseResources.GRILL);
			add(BaseResources.MOTEUR);
			add(BaseResources.CLIMATISATION);
			add(BaseResources.PILE);
			add(BaseResources.PANNEAU_SOLAIRE);
			add(BaseResources.PROCESSEUR);
		}
	};

	private Level level;
	private GameController controller;

	private Pack product;
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	private ArrayList<Resource> recipes = new ArrayList<Resource>();

	public Constructor_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;

		try {
			// Query for all the packages that are associated to this device
			final List<Pack> list = Database.daoPacking().queryBuilder()
					.where().eq("device", model.getIdAppareilModel()).query();
			// If its size equals 0, then create the resource and save it in the
			// database
			if (list.size() == 0) {
				product = new Pack(BaseResources.NONE, 1, model);
				Database.daoPacking().create(product);
			}
			// Else we get at the first index the packing
			else {
				product = list.get(0);
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
		Pack tempo = new Pack(product.getRessource(), 0);

		if (!product.getRessource().equals(BaseResources.NONE)) {
			for (int level = 0; level < this.level.getNiveau()
					|| level < resATraiter.getQuantity(); level++) {

				for (int i = 0; i < resATraiter.getQuantity(); i++)
					resources.add(resATraiter.getRessource());

				if (controller.getPartieEnCours().getArgent() < 5
						+ Device.getElectricity())
					throw new MoneyException();

				if (checkIngredients()) {
					tempo.addQuantity(1);
					controller.setArgent(Device.getElectricity(), false);
					controller.findDevice(pointer).action(tempo);
				}
			}
		}
	}

	/**
	 * 
	 * Checks if thre are enough resources to built the product
	 * 
	 * @return boolean if the requires resources are available
	 * 
	 */
	private boolean checkIngredients() {
		// A temo stock to save the resources (in case there aren't enough
		// ingredients)
		ArrayList<Resource> stock = new ArrayList<Resource>();
		// We make the recipes list empty
		recipes = new ArrayList<Resource>();
		// Then we refill it with the current recipe
		for (int i = 0; i < product.getRessource().getRecipe().get(0)
				.getQuantity(); i++) {
			recipes.add(
					product.getRessource().getRecipe().get(0).getRessource());
		}
		for (int i = 0; i < product.getRessource().getRecipe().get(1)
				.getQuantity(); i++) {
			recipes.add(
					product.getRessource().getRecipe().get(1).getRessource());
		}

		// For the size of the recipe
		for (int j = 0; j < product.getRessource().getRecipe().size(); j++) {
			// If the resource is available in the stock
			if (resources.contains(recipes.get(j))) {
				// We add it to teh tempo stock and we remove it form the
				// storage
				stock.add(recipes.get(j));
				resources.remove(recipes.get(j));
			}
			// Else (the resource isn't there)
			else {
				// We add all the resources from the stock
				resources.addAll(stock);
				// We break the method by returning false (we can find enough
				// ingredients)
				return false;
			}
		}
		// If everyting worked (so there are enough ingredients), we return
		// true
		return true;
	}

	/**
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * 
	 * @param product the resource to set
	 */
	public void setProduit(Pack product) {
		if (Constructor_.acceptedResources.contains(product.getRessource())) {
			this.product = product;
			try {
				Database.daoPacking().update(product);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @return produit the current product od this device
	 */
	public Pack getProduit() {
		return product;
	}
}
