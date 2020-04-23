package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.view.JeuContr�le;

public class RightConveyor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.NONE,
			PointerTypes.NONE);

	public RightConveyor(DeviceModel model,
			JeuContr�le controller)
			throws FileNotFoundException {
		super(model, controller);

		// Todo: add behaviour

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
	}
}