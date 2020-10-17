package com.martinheywang.products.model.devices;

import java.util.Arrays;
import java.util.List;

import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.ActionCost;
import com.martinheywang.products.model.devices.annotations.Buildable;
import com.martinheywang.products.model.devices.annotations.DefaultTemplate;
import com.martinheywang.products.model.devices.annotations.Description;
import com.martinheywang.products.model.devices.annotations.Prices;
import com.martinheywang.products.model.templates.Template.PointerTypes;

import javafx.scene.Node;

@AccessibleName("Multi-Convoyeur")
@Description("Rassemble les ressources de plusieurs entrées en une sortie")
@Prices(build = "300", upgradeTo2 = "7000", upgradeTo3 = "150000", destroyAt1 = "300", destroyAt2 = "7000", destroyAt3 = "150000")
@DefaultTemplate(top = PointerTypes.ENTRY, right = PointerTypes.ENTRY, left = PointerTypes.ENTRY, bottom = PointerTypes.EXIT)
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
