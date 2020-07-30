package com.martinheywang.model.devices;

import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.DefaultTemplate;
import com.martinheywang.model.templates.Template.PointerTypes;

@AccessibleName("Convoyeur à droite")
@DefaultTemplate(top = PointerTypes.ENTRY, right = PointerTypes.EXIT)
public final class RightConveyor extends Conveyor {

	public RightConveyor(DeviceModel model) {
		super(model);
	}

}
