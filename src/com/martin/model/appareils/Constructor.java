package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.model.appareils.comportement.Constructor_;
import com.martin.view.JeuContrôle;

public class Constructor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.NONE,
			PointerTypes.ENTRY);

	public Constructor(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Constructor_(model, controller);
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setProduit(Resource res) throws NullPointerException {
		if (behaviour instanceof Constructor_) {
			((Constructor_) behaviour).setProduit(new Packing(res, 1));
		}
	}

	public Resource getProduit() throws NullPointerException {
		if (behaviour instanceof Constructor_)
			return ((Constructor_) behaviour).getProduit()
					.getRessource();
		return null;
	}
}
