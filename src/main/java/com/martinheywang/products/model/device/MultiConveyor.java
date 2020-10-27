package com.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.device.annotation.ActionCost;
import com.martinheywang.products.model.device.annotation.Buildable;
import com.martinheywang.products.model.device.annotation.DefaultTemplate;
import com.martinheywang.products.model.device.annotation.Description;
import com.martinheywang.products.model.device.annotation.Prices;
import com.martinheywang.products.model.template.Template.PointerType;

import javafx.scene.Node;

@Extension
@AccessibleName("Multi-Convoyeur")
@Description("Rassemble les ressources de plusieurs entrées en une sortie")
@Prices(build = "300", upgradeTo2 = "7000", upgradeTo3 = "150000", destroyAt1 = "300", destroyAt2 = "7000", destroyAt3 = "150000")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.ENTRY, left = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("7")
public class MultiConveyor extends Conveyor {

    public MultiConveyor(DeviceModel model) {
	super(model);
    }

    @Override
    public List<Node> getWidgets() {
	return Arrays.asList();
    }

}
