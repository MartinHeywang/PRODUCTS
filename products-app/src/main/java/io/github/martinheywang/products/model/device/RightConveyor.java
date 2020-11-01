package io.github.martinheywang.products.model.device;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

@Extension(ordinal = 4)
@AccessibleName("Convoyeur à droite")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.EXIT)
public final class RightConveyor extends Conveyor {

    public RightConveyor(DeviceModel model) {
	super(model);
    }

}
