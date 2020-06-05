package com.martinheywang.model.devices.behaviours;

import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Furnace_ extends Behaviour {

	private Level level;
	private GameController controller;

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

	public Furnace_(Device device, GameController controller) {
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
						.valueOf("LINGOT_DE_" + resATraiter.getRessource()));

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
	 * <strong>NOTE : INGOT_OF_name must exists.</strong> If you add IRON
	 * to the list, the INGOT_OF_IRON must exists in the same enum.
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
