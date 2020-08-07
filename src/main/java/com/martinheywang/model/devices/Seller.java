package com.martinheywang.model.devices;

import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.ActionCost;
import com.martinheywang.model.devices.annotations.Buildable;
import com.martinheywang.model.devices.annotations.DefaultTemplate;
import com.martinheywang.model.devices.annotations.Description;
import com.martinheywang.model.devices.annotations.Prices;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.templates.Template.PointerTypes;

import javafx.scene.Node;

@AccessibleName("Vendeur")
@Description("Le vendeur vend toutes les ressources qui lui parvient.")
@Buildable
@DefaultTemplate(top = PointerTypes.ENTRY)
@ActionCost
@Prices(build = "500", upgradeTo2 = "10000", upgradeTo3 = "150000", destroyAt1 = "450", destroyAt2 = "9000", destroyAt3 = "140000")
public final class Seller extends Device {

    public Seller(DeviceModel model) {
	super(model);
    }

    @Override
    public void act(Pack packs) throws MoneyException {

	this.gameManager.addMoney(packs.getResource().getPrice());
	// Todo: improve the pulsing effect of the seller
	this.setActive(true);
	this.setActive(false);
    }

    @Override
    public List<Node> getWidgets() {
	return Arrays.asList();
    }

}
