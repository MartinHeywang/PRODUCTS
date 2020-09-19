package com.martinheywang.products.model.devices;

import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.DefaultTemplate;
import com.martinheywang.products.model.templates.Template.PointerTypes;

@AccessibleName("Convoyeur à gauche")
@DefaultTemplate(top = PointerTypes.ENTRY, left = PointerTypes.EXIT)
public final class LeftConveyor extends Conveyor {

	public LeftConveyor(DeviceModel model) {
		super(model);
	}
}
