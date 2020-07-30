package com.martinheywang.model.devices;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.ActionCost;
import com.martinheywang.model.exceptions.MoneyException;

@AccessibleName("Sol")
@ActionCost("0")
public final class Floor extends Device {

	public Floor(DeviceModel model) {
		super(model);
	}

	@Override
	public void act(Pack resources) throws MoneyException {
		// It does nothing...
	}

}
