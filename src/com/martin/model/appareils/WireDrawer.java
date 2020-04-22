package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.view.JeuContrôle;

public class WireDrawer extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,
			PointerTypes.NONE);

	public WireDrawer(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		// Todo: add behaviour

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
	}

}