package com.martinheywang.products.model.devices;

import java.util.Arrays;
import java.util.List;

import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.ActionCost;
import com.martinheywang.products.model.exceptions.MoneyException;

import javafx.scene.Node;

@AccessibleName("Sol")
@ActionCost("0")
public final class Floor extends Device {

    public Floor(DeviceModel model) {
        super(model);
    }

    @Override
    public boolean act(Pack resources) throws MoneyException {
        // It does nothing...
        return false;
    }

    @Override
    public List<Node> getWidgets() {
        return Arrays.asList();
    }

}
