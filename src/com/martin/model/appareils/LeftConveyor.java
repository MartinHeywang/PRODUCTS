package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.model.appareils.comportement.Conveyor_;
import com.martin.view.JeuContrôle;

public class LeftConveyor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.EXIT);

	public LeftConveyor(DeviceModel model,
			JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Conveyor_(model, controller);
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}
}
