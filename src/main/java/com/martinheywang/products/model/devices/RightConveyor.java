package com.martinheywang.products.model.devices;

import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.DefaultTemplate;
import com.martinheywang.products.model.templates.Template.PointerTypes;

@AccessibleName("Convoyeur à droite")
@DefaultTemplate(top = PointerTypes.ENTRY, right = PointerTypes.EXIT)
public final class RightConveyor extends Conveyor {

	public RightConveyor(DeviceModel model) {
		super(model);
	}

}
