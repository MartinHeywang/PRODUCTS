package com.martinheywang.model.behaviours;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.DefaultResource;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.GameController;

import javafx.scene.Node;

public class Constructor extends Behaviour {

	public static List<Resource> acceptedResources = new ArrayList<Resource>();

	private Pack product;
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	private ArrayList<Resource> recipes = new ArrayList<Resource>();

	public Constructor(Device device, GameController controller) {
		super(device, controller);

		try {
			final List<Pack> list = Database.createDao(Pack.class)
					.queryBuilder()
					.where().eq("device", model.getID()).query();
			if (list.size() == 0) {
				product = new Pack(DefaultResource.NONE, 1, model);
				Database.createDao(Pack.class).create(product);
			} else {
				product = list.get(0);
			}

		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());

		}
	}

	@Override
	public void action(Pack resATraiter)
			throws MoneyException {
		final Coordinate exit = template.getPointersFor(PointerTypes.EXIT)
				.get(0);

		Pack tempo = new Pack(product.getResource(), 0);

		if (!product.getResource().equals(DefaultResource.NONE)) {
			for (int level = 0; level < this.level.getValue()
					|| level < resATraiter.getQuantity(); level++) {

				for (int i = 0; i < resATraiter.getQuantity(); i++)
					resources.add(resATraiter.getResource());

				if (controller.getMoney()
						.compareTo(BigInteger
								.valueOf(5 + Device.getElectricity())) == -1)
					throw new MoneyException();
				else {
					if (checkIngredients()) {
						tempo.addQuantity(1);
						controller.removeMoney(
								BigInteger.valueOf(Device.getElectricity()));
						controller.findDevice(exit).action(tempo);
					}
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
		for (int i = 0; i < product.getResource().getRecipe().get(0)
				.getQuantity(); i++) {
			recipes.add(
					product.getResource().getRecipe().get(0).getResource());
		}
		for (int i = 0; i < product.getResource().getRecipe().get(1)
				.getQuantity(); i++) {
			recipes.add(
					product.getResource().getRecipe().get(1).getResource());
		}

		// For the size of the recipe
		for (int j = 0; j < product.getResource().getRecipe().size(); j++) {
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

	@Override
	public List<Node> getWidgets() {
		return null;
	}

	/**
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * 
	 * @param product the resource to set
	 */
	public void setProduct(Pack product) {
		if (Constructor.acceptedResources.contains(product.getResource())) {
			this.product.setResource(product.getResource());
			this.product.setQuantity(product.getQuantity());
		}

	}

	/**
	 * 
	 * @return produit the current product od this device
	 */
	public Pack getProduct() {
		return product;
	}

	public static final void addAcceptedResource(Resource res) {
		acceptedResources.add(res);
	}

	public static final void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}
}
