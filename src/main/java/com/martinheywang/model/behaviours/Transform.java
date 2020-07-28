package com.martinheywang.model.behaviours;

import java.math.BigInteger;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.view.GameController;

import javafx.scene.Node;

public abstract class Transform extends AbstractBehaviour {

	public Transform(Device device, GameController controller) {
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
			else {
				if (getAcceptedResource().contains(resATraiter.getResource())) {
					final Pack tempo = new Pack();
					tempo.addQuantity(1);
					tempo.setResource(Resource
							.valueOf(
									getPrefix()
											+ resATraiter.getResource()
											+ getSufix()));

					controller.removeMoney(
							BigInteger.valueOf(Device.getElectricity()));
					device.activate();
					controller.findDevice(exit).action(tempo);
				}
			}
		}
	}

	@Override
	public final List<Node> getWidgets() {
		return null;
	}

	/**
	 * Returns the prefix that should be added to find the output
	 * resource.
	 * 
	 * @return a string
	 */
	public abstract String getPrefix();

	/**
	 * Returns the sufix that should be added to find the output resource.
	 * 
	 * @return a string
	 */
	public abstract String getSufix();

	/**
	 * Returns the list of accepted resources of this behaviour.
	 * 
	 * @return the accepted resources.
	 */
	public abstract List<Resource> getAcceptedResource();
}
