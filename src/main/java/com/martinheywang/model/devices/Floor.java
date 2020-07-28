package com.martinheywang.model.devices;

import java.math.BigInteger;

import com.martinheywang.model.Pack;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.templates.TemplateCreator;
import com.martinheywang.model.templates.TemplateModel;
import com.martinheywang.model.types.info.PricesModule;

import javafx.scene.image.Image;

public final class Floor extends Device {

	public Floor(DeviceModel model) {
		super(model);
	}

	@Override
	public void act(Pack resources) throws MoneyException {
		// It does nothing...
	}

	@Override
	public String getAccesibleName() {
		return "Sol";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public PricesModule getPrices() {
		// The floor isn't buildable nor destroyable, so nothing to specify
		// here
		return null;
	}

	@Override
	public BigInteger getActionCost() {
		return new BigInteger("0");
	}

	@Override
	public TemplateModel getTemplateModel() {
		return TemplateCreator.getSingleton()
				.getModel();
	}

	@Override
	public Image getView() {
		return new Image(getClass().getResourceAsStream(
				"/images" + getLevel().getURL() + "FLOOR.png"));
	}

	@Override
	public boolean isBuildable() {
		return false;
	}

}
