package com.martinheywang.model.behaviours;

import java.math.BigInteger;
import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.DefaultResource;
import com.martinheywang.view.GameController;

import javafx.scene.Node;

public class Seller extends Behaviour {

	public Seller(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resATraiter)
			throws MoneyException {
		for (int i = 0; i < this.level.getValue()
				|| i < resATraiter.getQuantity(); i++) {
			if (controller.getMoney()
					.compareTo(BigInteger
							.valueOf(5 + Device.getElectricity())) == -1)
				throw new MoneyException();
			else {
				if (!resATraiter.getResource().equals(DefaultResource.NONE)) {
					controller.addMoney(resATraiter.getResource().getPrice()
							.subtract(BigInteger
									.valueOf(Device.getElectricity())));
					device.activate();
				}
			}
		}
	}

	@Override
	public List<Node> getWidgets() {
		return null;
	}

}