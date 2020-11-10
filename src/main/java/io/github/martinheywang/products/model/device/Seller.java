package io.github.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import javafx.scene.Node;

/**
 * The seller is a {@link io.github.martinheywang.products.api.model.device.Device} that sells all the resource that he received.
 */
@Extension(ordinal = 2)
@AccessibleName("Vendeur")
@Description("Le vendeur vend toutes les ressources qui lui parvient.")
@Buildable
@DefaultTemplate(top = PointerType.ENTRY)
@ActionCost
@Prices(build = "500", upgradeTo2 = "10000", upgradeTo3 = "150000", destroyAt1 = "450", destroyAt2 = "9000", destroyAt3 = "140000")
public final class Seller extends Device {

    /**
	 * Creates a new Seller.
	 * 
	 * @param model a device model, where the type may be null.
	 */
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
