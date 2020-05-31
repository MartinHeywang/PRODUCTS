package com.martinheywang.model.devices.behaviours;

import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.DeviceModel;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class WireDrawer_ implements Behaviour {

	private Level level;
	private GameController controller;

	@SuppressWarnings("serial")
	public static List<Resource> acceptedResources = new ArrayList<Resource>() {
		{
			add(BaseResources.NONE);
			add(BaseResources.FER);
			add(BaseResources.OR);
			add(BaseResources.CUIVRE);
			add(BaseResources.ARGENT);
			add(BaseResources.ALUMINIUM);
		}
	};

	public WireDrawer_(DeviceModel model, GameController controller) {
		this.level = model.getNiveau();
		this.controller = controller;
	}

	@Override
	public void action(Pack resATraiter, Coordinates pointer)
			throws MoneyException {

		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.getQuantity(); i++) {
			if (acceptedResources.contains(resATraiter.getRessource())) {
				final Pack tempo = new Pack();
				tempo.addQuantity(1);
				tempo.setRessource(BaseResources
						.valueOf("FIL_DE_" + resATraiter.getRessource()));

				controller.setArgent(Device.getElectricity(), false);
				controller.findDevice(pointer).action(tempo);
			}
		}

	}

}
