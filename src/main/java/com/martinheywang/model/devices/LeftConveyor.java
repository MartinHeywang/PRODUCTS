package com.martinheywang.model.devices;

import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.DefaultTemplate;
import com.martinheywang.model.templates.Template.PointerTypes;

@AccessibleName("Convoyeur à gauche")
@DefaultTemplate(top = PointerTypes.ENTRY, left = PointerTypes.EXIT)
public final class LeftConveyor extends Conveyor {

	public LeftConveyor(DeviceModel model) {
		super(model);
	}
}
