package com.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.action.Action;
import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.device.annotation.ActionCost;
import com.martinheywang.products.model.exception.MoneyException;

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
