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
import com.martinheywang.products.model.templates.Template.PointerTypes;

import javafx.scene.Node;

@AccessibleName("Multi-Convoyeur")
@Description("Rassemble les ressources de plusieurs entrées en une sortie")
@Prices(build = "300", upgradeTo2 = "7000", upgradeTo3 = "150000", destroyAt1 = "300", destroyAt2 = "7000", destroyAt3 = "150000")
@DefaultTemplate(top = PointerTypes.ENTRY, right = PointerTypes.ENTRY, left = PointerTypes.ENTRY, bottom= PointerTypes.EXIT)
@Buildable
@ActionCost("5")
public class MultiConveyor extends Device {

	public MultiConveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public final void act(Pack resources) throws MoneyException {
		if (this.gameManager.getMoney().compareTo(this.getActionCost()) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}

		final Coordinate output = this.template.getPointersFor(PointerTypes.EXIT).get(0);

		if (this.gameManager.connectionExists(this.getPosition(), output)) {
			// Remove action cost
			this.gameManager.removeMoney(this.getActionCost());

			this.gameManager.performAction(this.getPosition(), output, resources);

			this.setActive(true);
			this.setActive(false);
		}
	}

	@Override
	public List<Node> getWidgets() {
		return Arrays.asList();
	}

}
