package io.github.martinheywang.products.model.device;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * A right-conveyor is a particular
 * {@link io.github.martinheywang.products.model.device.Conveyor} that has a
 * different
 * {@link io.github.martinheywang.products.api.model.template.Template}. It
 * takes an entry at the top and an exit to the right.
 */
@Extension(ordinal = 4)
@AccessibleName("Convoyeur à droite")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.EXIT)
public final class RightConveyor extends Conveyor {

    /**
	 * Creates a new RightConveyor.
	 * 
	 * @param model a device model, where the type may be null.
	 */
    public RightConveyor(DeviceModel model) {
        super(model);
    }

}
