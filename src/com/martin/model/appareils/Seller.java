package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Packing;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.model.appareils.comportement.Seller_;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Seller extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE);

	public Seller(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Seller_(model, controller);
	}

	@Override
	public void action(Packing resATraiter) throws MoneyException {
		behaviour.action(resATraiter, null);
		timeline.playFromStart();
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

}