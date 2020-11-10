package io.github.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import javafx.scene.Node;

/**
 * The multi-conveyor is a particular
 * {@link io.github.martinheywang.products.model.device.Conveyor}. It takes
 * multiple entries, and distribute the received resource to the only one exit.
 * 
 * @see io.github.martinheywang.products.api.model.device.Device
 */
@Extension(ordinal = 5)
@AccessibleName("Multi-Convoyeur")
@Description("Rassemble les ressources de plusieurs entrées en une sortie")
@Prices(build = "300", upgradeTo2 = "7000", upgradeTo3 = "150000", destroyAt1 = "300", destroyAt2 = "7000", destroyAt3 = "150000")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.ENTRY, left = PointerType.ENTRY, bottom = PointerType.EXIT)
@ActionCost("7")
public class MultiConveyor extends Conveyor {

    /**
     * Creates a new MultiConveyor.
     * 
     * @param model a device model, where the type may be null.
     */
    public MultiConveyor(DeviceModel model) {
        super(model);
    }

    @Override
    public List<Node> getWidgets() {
        return Arrays.asList();
    }

}
