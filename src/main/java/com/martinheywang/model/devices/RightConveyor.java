package com.martinheywang.model.devices;

import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.model.templates.TemplateCreator;
import com.martinheywang.model.templates.TemplateModel;

import javafx.scene.image.Image;

public class RightConveyor extends Conveyor {

	public RightConveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public String getAccesibleName() {
		return "Convoyeur à droite";
	}

	@Override
	public TemplateModel getTemplateModel() {
		return TemplateCreator.getSingleton()
				.setTop(PointerTypes.ENTRY)
				.setRight(PointerTypes.EXIT)
				.getModel();
	}

	@Override
	public Image getView() {
		return new Image(getClass().getResourceAsStream(
				"/images" + getLevel() + "RIGHT_CONVEYOR.png"));
	}

}
