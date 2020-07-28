package com.martinheywang.model.devices;

import java.math.BigInteger;

import com.martinheywang.model.Pack;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.model.templates.TemplateCreator;
import com.martinheywang.model.templates.TemplateModel;
import com.martinheywang.model.types.info.PricesModule;

import javafx.scene.image.Image;

public final class Seller extends Device {

	public Seller(DeviceModel model) {
		super(model);
	}

	@Override
	public void act(Pack packs) throws MoneyException {
		this.gameManager.addMoney(packs.getResource().getPrice());
	}

	@Override
	public String getAccesibleName() {
		return "Vendeur";
	}

	@Override
	public String getDescription() {
		return "Le vendeur vend toutes les resources qu'il reçoit. "
				+ "Placez le en fin de ligne !";
	}

	@Override
	public PricesModule getPrices() {
		return new PricesModule("500", "10000", "150000", "400", "9500",
				"140000");
	}

	@Override
	public BigInteger getActionCost() {
		return new BigInteger("5");
	}

	@Override
	public TemplateModel getTemplateModel() {
		return TemplateCreator.getSingleton()
				.setTop(PointerTypes.ENTRY)
				.getModel();
	}

	@Override
	public Image getView() {
		return new Image(getClass().getResourceAsStream(
				"/images" + getLevel() + "SELLER.png"));
	}

	@Override
	public boolean isBuildable() {
		return true;
	}

}
