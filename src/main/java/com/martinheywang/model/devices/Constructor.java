package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.Packing;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Constructor_;
import com.martinheywang.view.GameController;

public class Constructor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.NONE,
			PointerTypes.ENTRY);

	public Constructor(DeviceModel model, GameController controller)
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
