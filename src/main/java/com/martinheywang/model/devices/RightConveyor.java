package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.devices.behaviours.Conveyor_;
import com.martinheywang.view.GameController;

public class RightConveyor extends Device {

	public RightConveyor(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new Conveyor_(this, controller);
	}
}
