package com.martinheywang.model.behaviours;

import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

import javafx.scene.Node;

public class Sorter extends AbstractBehaviour {

	public Sorter(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public void action(Pack resource)
			throws MoneyException {

	}

	@Override
	public List<Node> getWidgets() {
		return null;
	}

}
