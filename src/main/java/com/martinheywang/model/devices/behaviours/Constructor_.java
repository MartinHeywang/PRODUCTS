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

public class Constructor_ implements Behaviour {
	private Level level;
	private GameController controller;

	private Packing product;
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	private ArrayList<Resource> recipes = new ArrayList<Resource>();

	public Constructor_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;

		try {
			// Query for all the packages that are associated to this device
			final List<Packing> list = Database.daoPacking().queryBuilder()
					.where().eq("device", model.getIdAppareilModel()).query();
			// If its size equals 0, then create the resource and save it in the
			// database
			if (list.size() == 0) {
				product = new Packing(Resource.NONE, 1, model);
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
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {
		Packing tempo = new Packing(product.getRessource(), 0);

		if (!product.getRessource().equals(Resource.NONE)) {
			for (int level = 0; level < this.level.getNiveau()
					|| level < resATraiter.getQuantity(); level++) {

				for (int i = 0; i < resATraiter.getQuantity(); i++)
					resources.add(resATraiter.getRessource());

				if (controller.getPartieEnCours().getArgent() < 5
						+ Device.getElectricity())
					throw new MoneyException(
							"Le comportement d'un acheteur "
									+ "n'a pas pu �tre r�alis� car le solde "
									+ "d'argent n'�tait pas assez important.");

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
	 * <h1>checkIngr�dients</h1>
	 * <p>
	 * Checks if thre are enough resources to built the product
	 * </p>
	 * 
	 * @return boolean if the requires resources are available
	 * 
	 */
	private boolean checkIngredients() {
		// Le stock temporaire pour mettre les de c�t� les ressources
		// r�serv�es au produit
		ArrayList<Resource> stock = new ArrayList<Resource>();
		// On vide les �l�ments de la recette
		recipes = new ArrayList<Resource>();
		// Puis on la re-remplie en fonction des ressources de la quantit�
		// Cette appareil prend en charge tous les sch�ma � 2 paquets
		for (int i = 0; i < product.getRessource().getRecette().get(0)
				.getQuantity(); i++) {
			recipes.add(
					product.getRessource().getRecette().get(0).getRessource());
		}
		for (int i = 0; i < product.getRessource().getRecette().get(1)
				.getQuantity(); i++) {
			recipes.add(
					product.getRessource().getRecette().get(1).getRessource());
		}

		// Pour la taille de la recette cr�e
		for (int j = 0; j < product.getRessource().getRecette().size(); j++) {
			// Si la ressource est pr�sente dans le stock
			if (resources.contains(recipes.get(j))) {
				// On l'ajoute au stock temporaire et on l'enl�ve du stockage
				stock.add(recipes.get(j));
				resources.remove(recipes.get(j));
			}
			// Sinon...
			else {
				// On remet les ressource du stock dans le stockage principal
				resources.addAll(stock);
				// On retourne faux pour dire que l'action ne peut pas continuer
				// (les ressources ne sont pas suffisantes)
				return false;
			}
		}
		// On retourne vrai car toutes les ressources n�cessaires sont
		// disponibles
		return true;
	}

	/**
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * 
	 * @param produit the resource to set
	 */
	public void setProduit(Packing produit) {
		switch (produit.getRessource()) {
		case FER:
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			this.product = produit;
			try {
				Database.daoPacking().update(produit);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		default:
			break;
		}

	}

	/**
	 * 
	 * @return produit the current product od this device
	 */
	public Packing getProduit() {
		return product;
	}
}
