package com.martinheywang.model.behaviours;

import java.math.BigInteger;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.view.GameController;

import javafx.scene.Node;

public class Conveyor extends Behaviour {

	private Level level;
	private GameController controller;

	public Conveyor(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resATraiter)
			throws MoneyException {
		final Coordinate exit = template.getPointersFor(PointerTypes.EXIT)
				.get(0);

		for (int i = 0; i < this.level.getValue()
				|| i < resATraiter.getQuantity(); i++) {
			if (controller.getMoney()
					.compareTo(BigInteger
							.valueOf(5 + Device.getElectricity())) == -1)
				throw new MoneyException();

			controller.removeMoney(BigInteger.valueOf(Device.getElectricity()));
		}
		controller.findDevice(exit).action(resATraiter);
	}

	@Override
	public List<Node> getWidgets() {
		return null;
	}

}
