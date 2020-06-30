package com.martinheywang.model.devices.behaviours;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.database.Saver;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.BaseResources;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.GameController;

/**
 * @author Martin Heywang
 *
 */
public class Buyer_ extends Behaviour {

	/**
	 * This {@link List} defines which Resource are accepted by this
	 * device and which are not. It has a set of default, but you can add
	 * and remove some by calling
	 * {@link Buyer_#addAcceptedResource(Resource)} or
	 * {@link Buyer_#removeAcceptedResource(Resource)}.
	 * 
	 * @see Resource
	 */
	@SuppressWarnings("serial")
	public static List<Resource> acceptedResources = new ArrayList<Resource>() {
		{
			add(BaseResources.NONE);
			add(BaseResources.IRON);
			add(BaseResources.GOLD);
			add(BaseResources.COPPER);
			add(BaseResources.SILVER);
			add(BaseResources.DIAMOND);
			add(BaseResources.ALUMINIUM);
		}
	};

	private Pack distributedResource;

	public Buyer_(Device device, GameController controller) {
		super(device, controller);

		distributedResource = new Pack(BaseResources.NONE, 1,
				device.getModel());

		try {
			loadDistributedResource();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());

		}
	}

	private void loadDistributedResource() throws SQLException {
		final List<Pack> list = Database.createDao(Pack.class).queryBuilder()
				.where().eq("device", this.model.getID()).query();
		if (list.size() == 0) {
			distributedResource = new Pack(BaseResources.NONE, 1, model);
			Saver.savePack(distributedResource);
		} else {
			distributedResource = list.get(0);
		}
	}

	@Override
	public void action(Pack resATraiter, Coordinate pointer)
			throws MoneyException {

		distributedResource.setQuantity(0);

		for (int niveau = 0; niveau < this.level.getValue(); niveau++) {

			if (controller.getMoney()
					.compareTo(BigInteger
							.valueOf(5 + Device.getElectricity())) == -1)
				throw new MoneyException();
			else {
				if (!distributedResource.getResource()
						.equals(BaseResources.NONE)) {
					distributedResource.addQuantity(1);
					device.activate();
					controller.removeMoney(
							BigInteger.valueOf(5 + Device.getElectricity()));
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
		if (acceptedResources.contains(pack.getResource())) {
			this.distributedResource.setResource(pack.getResource());

			this.distributedResource.setQuantity(pack.getQuantity());
			try {
				Saver.savePack(distributedResource);
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

	/**
	 * Adds a Resource in the white list of accepted Resource for this
	 * device. This method is protected against adding multiple times the
	 * exact same {@link Resource}.
	 * 
	 * @param res the Resource to add
	 */
	public static void addAcceptedResource(Resource res) {
		if (!acceptedResources.contains(res))
			acceptedResources.add(res);

	}

	/**
	 * Removes of the white list the given Resource.
	 * 
	 * @param res
	 */
	public static void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}
}