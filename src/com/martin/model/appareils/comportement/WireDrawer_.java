package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.Stock;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class WireDrawer_ implements Behaviour {

	private Level level;
	private JeuContrôle controller;

	public WireDrawer_(Coordinates xy, Level level,
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.level = level;
		this.controller = controller;
	}

	@Override
	public void action(Stock resATraiter, Coordinates pointer)
			throws MoneyException {
		final Stock tempoStock = new Stock();

		for (int i = 0; i < this.level.getNiveau(); i++) {
			switch (resATraiter.get(i).getRessource()) {
			case FER:
			case OR:
			case CUIVRE:
			case ARGENT:
			case ALUMINIUM:
				tempoStock.add(new Packing(Resource.valueOf(
						"FIL_DE_" + resATraiter.get(i).getRessource()),
						resATraiter.get(i).getQuantité()));

				controller.setArgent(Device.getElectricity(), false);
				break;
			default:
				break;
			}
		}
		controller.findDevice(pointer).action(tempoStock);

	}

}
