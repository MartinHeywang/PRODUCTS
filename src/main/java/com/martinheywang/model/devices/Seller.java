package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Seller_;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Seller extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE);

	public Seller(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Seller_(model, controller);
	}

	@Override
	public void action(Pack resATraiter) throws MoneyException {
		behaviour.action(resATraiter, null);
		timeline.playFromStart();
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

}