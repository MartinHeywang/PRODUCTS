package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.devices.behaviours.Press_;
import com.martinheywang.view.GameController;

public class Press extends Device {

	public Press(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new Press_(this, controller);
	}
}