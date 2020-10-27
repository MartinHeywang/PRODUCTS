package com.martinheywang.products.model.device;

import org.pf4j.Extension;

import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.device.annotation.DefaultTemplate;
import com.martinheywang.products.model.template.Template.PointerType;

@Extension
@AccessibleName("Convoyeur à gauche")
@DefaultTemplate(top = PointerType.ENTRY, left = PointerType.EXIT)
public final class LeftConveyor extends Conveyor {

    public LeftConveyor(DeviceModel model) {
	super(model);
    }
}
