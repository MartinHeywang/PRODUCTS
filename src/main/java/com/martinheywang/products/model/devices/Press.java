package com.martinheywang.products.model.devices;

import java.util.Arrays;
import java.util.List;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.ActionCost;
import com.martinheywang.products.model.devices.annotations.Buildable;
import com.martinheywang.products.model.devices.annotations.DefaultTemplate;
import com.martinheywang.products.model.devices.annotations.Description;
import com.martinheywang.products.model.devices.annotations.Prices;
import com.martinheywang.products.model.exceptions.MoneyException;
import com.martinheywang.products.model.resources.DefaultResource;
import com.martinheywang.products.model.resources.Resource;
import com.martinheywang.products.model.resources.ToPlate;
import com.martinheywang.products.model.templates.Template.PointerTypes;

import javafx.scene.Node;

@AccessibleName("Presse")
@Description("Le presse transforme les resources qui lui parviennent en plaques.")
@Prices(build = "2000", upgradeTo2 = "15000", upgradeTo3 = "500000", destroyAt1 = "1800", destroyAt2 = "13000", destroyAt3 = "450000")
@DefaultTemplate(top = PointerTypes.ENTRY, bottom = PointerTypes.EXIT)
@Buildable
@ActionCost("8")
public class Press extends Device {

	public Press(DeviceModel model) {
		super(model);
	}

	@Override
	public final boolean act(Pack resources) throws MoneyException {
		if (this.gameManager.getMoney().compareTo(this.getActionCost()) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}
		// If the given pack cannot be smelted
		if (!resources.getResource().hasAnnotation(ToPlate.class))
			return false;

		final ToPlate annotation = resources.getResource().getField().getAnnotation(ToPlate.class);
		Resource transformed;
		try {
			transformed = (Resource) annotation.clazz().getField(annotation.field()).get(null);
		} catch (final Exception e) {
			e.printStackTrace();
			transformed = DefaultResource.NONE;
		}

		final Coordinate output = this.template.getPointersFor(PointerTypes.EXIT).get(0);

		if (this.gameManager.connectionExists(this.getPosition(), output)) {
			// Remove action cost
			this.gameManager.removeMoney(this.getActionCost());

			this.gameManager.performAction(this.getPosition(), output, new Pack(transformed, resources.getQuantity()));
			return true;
		}

		return false;
	}

	@Override
	public final List<Node> getWidgets() {
		return Arrays.asList();
	}
}