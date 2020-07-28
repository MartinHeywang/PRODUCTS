package com.martinheywang.model.devices;

import java.math.BigInteger;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.model.templates.TemplateCreator;
import com.martinheywang.model.templates.TemplateModel;
import com.martinheywang.model.types.info.PricesModule;

import javafx.scene.image.Image;

public class Conveyor extends Device {

	public Conveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public void act(Pack resources) throws MoneyException {
		if (gameManager.getMoney()
				.compareTo(getActionCost()) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}

		final Coordinate output = this.template
				.getPointersFor(PointerTypes.ENTRY).get(0);

		// Remove action cost
		gameManager.removeMoney(getActionCost());

		gameManager.performAction(getPosition(), output,
				resources);
	}

	@Override
	public String getAccesibleName() {
		return "Convoyeur";
	}

	@Override
	public String getDescription() {
		return "Le convoyeur transporte les resources d'un point à l'autre";
	}

	@Override
	public PricesModule getPrices() {
		return new PricesModule("200", "5000", "100000", "200", "5000",
				"100000");
	}

	@Override
	public BigInteger getActionCost() {
		return new BigInteger("5");
	}

	@Override
	public TemplateModel getTemplateModel() {
		return TemplateCreator.getSingleton()
				.setTop(PointerTypes.ENTRY)
				.setBottom(PointerTypes.EXIT)
				.getModel();
	}

	@Override
	public Image getView() {
		return new Image(getClass().getResourceAsStream(
				"/images" + getLevel().getURL() + "CONVEYOR.png"));
	}

	@Override
	public boolean isBuildable() {
		return true;
	}

}
