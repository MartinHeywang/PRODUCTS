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

@AccessibleName("Convoyeur")
@Description("Le convoyeur transporte les ressources d'u point A à un point B")
@Prices(build = "100", upgradeTo2 = "5000", upgradeTo3 = "100000", destroyAt1 = "100", destroyAt2 = "5000", destroyAt3 = "100000")
@DefaultTemplate(top = PointerTypes.ENTRY, bottom = PointerTypes.EXIT)
@Buildable
@ActionCost("5")
public class Conveyor extends Device {

	public Conveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public final boolean act(Pack resources) throws MoneyException {
		if (this.gameManager.getMoney().compareTo(this.getActionCost()) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}

		final Coordinate output = this.template.getPointersFor(PointerTypes.EXIT).get(0);

		if (this.gameManager.connectionExists(this.getPosition(), output)) {
			// Remove action cost
			this.gameManager.removeMoney(this.getActionCost());

			this.gameManager.performAction(this.getPosition(), output, resources);
			return true;
		}
		return false;
	}

	@Override
	public List<Node> getWidgets() {
		return Arrays.asList();
	}

}
