package com.martinheywang.model.devices;

import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.model.templates.TemplateCreator;
import com.martinheywang.model.templates.TemplateModel;

import javafx.scene.image.Image;

public class LeftConveyor extends Conveyor {

	public LeftConveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public String getAccesibleName() {
		return "Convoyeur à gauche";
	}

	@Override
	public TemplateModel getTemplateModel() {
		return TemplateCreator.getSingleton()
				.setTop(PointerTypes.ENTRY)
				.setLeft(PointerTypes.EXIT)
				.getModel();
	}

	@Override
	public Image getView() {
		return new Image(getClass().getResourceAsStream(
				"/images" + getLevel() + "LEFT_CONVEYOR.png"));
	}

}
