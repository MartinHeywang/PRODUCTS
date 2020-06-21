package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.devices.behaviours.Furnace_;
import com.martinheywang.view.GameController;

public class Furnace extends Device {

	public Furnace(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new Furnace_(this, controller);
	}
}
