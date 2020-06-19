package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Conveyor_;
import com.martinheywang.view.GameController;

public class LeftConveyor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.EXIT);

	public LeftConveyor(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Conveyor_(this, controller);
	}
}
