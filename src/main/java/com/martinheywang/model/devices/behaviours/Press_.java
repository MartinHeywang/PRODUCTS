package com.martinheywang.model.devices.behaviours;

import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Press_ extends Behaviour {

	/**
	 * This {@link List} defines which Resource are accepted by this
	 * device and which are not. It has a set of default, but you can add
	 * and remove some by calling
	 * {@link Press_#addAcceptedResource(Resource)} or
	 * {@link Press_#removeAcceptedResource(Resource)}.
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
			add(BaseResources.ALUMINIUM);
		}
	};

	public Press_(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resATraiter, Coordinates pointer)
			throws MoneyException {

		for (int i = 0; i < this.level.getValue()
				|| i < resATraiter.getQuantity(); i++) {
			if (acceptedResources.contains(resATraiter.getRessource())) {
				final Pack tempo = new Pack();
				tempo.addQuantity(1);
				tempo.setRessource(BaseResources
						.valueOf("PLAQUE_DE_" + resATraiter.getRessource()));

				controller.removeMoney(Device.getElectricity());
				controller.findDevice(pointer).action(tempo);
			}
		}

	}

	/**
	 * Adds a {@link Resource} to the list of accepted resources. It
	 * basically means that all the press will be able to make plate of
	 * this resources.<br>
	 * <br>
	 * <strong>NOTE : PLATE_OF_name must exists.</strong> If you add IRON
	 * to the list, the PLATE_OF_IRON must exists in the same enum.
	 * 
	 * 
	 * @param res the res to add
	 */
	public static void addAcceptedResource(Resource res) {
		if (!acceptedResources.contains(res)) {
			acceptedResources.add(res);
		}
	}

	/**
	 * Removes of the white list the given resource.
	 * 
	 * @param res
	 */
	public static void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}

}