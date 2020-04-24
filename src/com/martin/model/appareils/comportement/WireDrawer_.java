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

		for (int i = 0; i < this.level.getNiveau()
				|| i < resATraiter.getQuantity(); i++) {
			switch (resATraiter.getRessource()) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				final Packing tempo = new Packing();
				tempo.addQuantity(1);
				tempo.setRessource(Resource
						.valueOf("FIL_DE_" + resATraiter.getRessource()));

				controller.setArgent(Device.getElectricity(), false);
				controller.findDevice(pointer).action(tempo);
				break;
			default:
				break;
			}
		}

	}

}
