package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Stock;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Seller extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE);

	public Seller(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		// Todo : test behaviour

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {
		System.out.println("action(Stock) in type Seller");
		behaviour.action(resATraiter, null);
	}

}