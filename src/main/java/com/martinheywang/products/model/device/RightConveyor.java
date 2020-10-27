package com.martinheywang.products.model.device;

import org.pf4j.Extension;

import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.device.annotation.DefaultTemplate;
import com.martinheywang.products.model.template.Template.PointerType;

@Extension
@AccessibleName("Convoyeur à droite")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.EXIT)
public final class RightConveyor extends Conveyor {

    public RightConveyor(DeviceModel model) {
	super(model);
    }

}
