package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.DeviceModel;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class WireDrawer_ implements Behaviour {

	private Level level;
	private JeuContrôle controller;

	public WireDrawer_(DeviceModel model, JeuContrôle controller) {
		this.level = model.getNiveau();
		this.controller = controller;
	}

	@Override
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {
		// Todo : copy behaviour from press

		final Packing tempo = new Packing(Resource.valueOf(
				"FIL_DE_" + resATraiter.getRessource()), 0);

		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.getQuantity(); i++) {
			switch (resATraiter.getRessource()) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				tempo.addQuantity(1);

				controller.setArgent(Device.getElectricity(), false);
				break;
			default:
				break;
			}
		}
		controller.findDevice(pointer).action(tempo);

	}

}
