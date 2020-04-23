package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.DeviceModel;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Conveyor_ implements Behaviour {

	private Level level;
	private JeuContrôle controller;

	public Conveyor_(DeviceModel model, JeuContrôle controller) {
		this.level = model.getNiveau();
		this.controller = controller;
	}

	@Override
	public void action(Packing resATraiter, Coordinates pointer)
			throws MoneyException {
		// Xxx : check '>' operator if not working properly
		for (int niveau = 0; niveau < this.level.getNiveau() || resATraiter
				.getQuantity() > niveau; niveau++) {
			if (controller.getPartieEnCours().getArgent() < 5
					+ Device.getElectricity())
				throw new MoneyException(
						"Le comportement d'un appareil "
								+ "n'a pas pu être réalisé car le solde "
								+ "d'argent n'était pas assez important.");

			controller.setArgent(Device.getElectricity(), false);
		}
		controller.findDevice(pointer).action(resATraiter);
	}

}
