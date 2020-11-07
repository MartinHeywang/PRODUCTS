package io.github.martinheywang.products.model.device;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

@Extension(ordinal = 4)
@AccessibleName("Convoyeur à gauche")
@DefaultTemplate(top = PointerType.ENTRY, left = PointerType.EXIT)
public final class LeftConveyor extends Conveyor {

    public LeftConveyor(DeviceModel model) {
	super(model);
    }
}
