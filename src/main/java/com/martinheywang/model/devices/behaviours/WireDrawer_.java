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

public class WireDrawer_ extends Behaviour {

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

	public WireDrawer_(Device device, GameController controller) {
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
						.valueOf("FIL_DE_" + resATraiter.getRessource()));

				controller.removeMoney(Device.getElectricity());
				controller.findDevice(pointer).action(tempo);
			}
		}

	}

}
