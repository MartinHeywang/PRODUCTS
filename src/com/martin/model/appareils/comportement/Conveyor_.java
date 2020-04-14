package com.martin.model.appareils.comportement;

import com.martin.model.Coordinates;
import com.martin.model.Stock;
import com.martin.model.appareils.Device;
import com.martin.model.appareils.Level;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Conveyor_ implements Behaviour {

	private Coordinates pointer;
	private Level level;
	private JeuContrôle controller;

	public Conveyor_(Coordinates xy, Level level,
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.level = level;
		this.controller = controller;
		this.pointer = new Coordinates(xy.getX() + xToAdd, xy.getY() + yToAdd);
	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {
		for (int niveau = 0; niveau < this.level.getNiveau() || resATraiter
				.size() < niveau; niveau++) {
			if (controller.getPartieEnCours().getArgent() < 5
					+ Device.getElectricity())
				throw new MoneyException(
						"Le comportement d'un appareil "
								+ "n'a pas pu être réalisé car le solde "
								+ "d'argent n'était pas assez important.");

			controller.setArgent(Device.getElectricity(), false);
		}
		controller.getPartieEnCours().getAppareil(pointer).action(resATraiter);
	}

}
