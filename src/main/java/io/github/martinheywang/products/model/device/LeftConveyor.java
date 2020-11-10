package io.github.martinheywang.products.model.device;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.template.Template.PointerType;

/**
 * A left-conveyor is a modified version of the {@link io.github.martinheywang.products.model.device.Conveyor} that has a different
 * {@link io.github.martinheywang.products.api.model.template.Template}. It
 * takes an entry at the top, and an exit on the left.
 * 
 * @see io.github.martinheywang.products.api.model.device.Device
 */
@Extension(ordinal = 4)
@AccessibleName("Convoyeur à gauche")
@DefaultTemplate(top = PointerType.ENTRY, left = PointerType.EXIT)
public final class LeftConveyor extends Conveyor {

    /**
	 * Creates a new LeftConveyor.
	 * 
	 * @param model a device model, where the type may be null.
	 */
    public LeftConveyor(DeviceModel model) {
        super(model);
    }
}
