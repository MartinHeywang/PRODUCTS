package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.devices.behaviours.WireDrawer_;
import com.martinheywang.view.GameController;

public class WireDrawer extends Device {

	public WireDrawer(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new WireDrawer_(this, controller);
	}

}