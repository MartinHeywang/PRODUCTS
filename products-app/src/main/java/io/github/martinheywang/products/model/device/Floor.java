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
import io.github.martinheywang.products.api.model.exception.MoneyException;
import javafx.scene.Node;

@Extension
@AccessibleName("Sol")
@ActionCost("0")
public final class Floor extends Device {

    public Floor(DeviceModel model) {
	super(model);
    }

    @Override
    public Action act(Pack resources) throws MoneyException {
	/*
	 * The floor is a special device that does obviously nothing. But it doesn't
	 * have any entry or exit, so it cannot be triggered by anything. To avoid some
	 * NullPointerException, we'll just return an empty Action.
	 */
	return new Action(this, resources);
    }

    @Override
    public List<Node> getWidgets() {
	return Arrays.asList();
    }

}
