package com.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.action.Action;
import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.device.annotation.ActionCost;
import com.martinheywang.products.model.device.annotation.Buildable;
import com.martinheywang.products.model.device.annotation.DefaultTemplate;
import com.martinheywang.products.model.device.annotation.Description;
import com.martinheywang.products.model.device.annotation.Prices;
import com.martinheywang.products.model.exception.MoneyException;
import com.martinheywang.products.model.template.Template.PointerType;

import javafx.scene.Node;

@Extension
@AccessibleName("Vendeur")
@Description("Le vendeur vend toutes les ressources qui lui parvient.")
@Buildable
@DefaultTemplate(top = PointerType.ENTRY)
@ActionCost
@Prices(build = "500", upgradeTo2 = "10000", upgradeTo3 = "150000", destroyAt1 = "450", destroyAt2 = "9000", destroyAt3 = "140000")
public final class Seller extends Device {

    public Seller(DeviceModel model) {
	super(model);
    }

    @Override
    public Action act(Pack packs) throws MoneyException {
	final Action action = new Action(this, packs);
	action.addCost(
		packs.getResource().getPrice().multiply(packs.getQuantity()).subtract(this.getActionCost()).negate());
	action.markAsSuccessful();

	return action;
    }

    @Override
    public List<Node> getWidgets() {
	return Arrays.asList();
    }

}
