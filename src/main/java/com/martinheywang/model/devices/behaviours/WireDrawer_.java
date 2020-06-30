package com.martinheywang.model.devices.behaviours;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.BaseResources;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.GameController;

public class WireDrawer_ extends Behaviour {

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
	public void action(Pack resATraiter, Coordinate pointer)
			throws MoneyException {

		for (int i = 0; i < this.level.getValue()
				|| i < resATraiter.getQuantity(); i++) {
			if (controller.getMoney()
					.compareTo(BigInteger
							.valueOf(5 + Device.getElectricity())) == -1)
				throw new MoneyException();
			else {
				if (acceptedResources.contains(resATraiter.getResource())) {
					final Pack tempo = new Pack();
					tempo.addQuantity(1);
					tempo.setResource(BaseResources
							.valueOf(resATraiter.getResource() + "_WIRE"));

					controller.removeMoney(
							BigInteger.valueOf(Device.getElectricity()));
					device.activate();
					controller.findDevice(pointer).action(tempo);
				}
			}
		}

	}

}
