package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.behaviours.Seller_;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.GameController;

public class Seller extends Device {

	public Seller(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new Seller_(this, controller);
	}

	@Override
	public void action(Pack resATraiter) throws MoneyException {
		behaviour.action(resATraiter, null);
		timeline.playFromStart();
	}

}