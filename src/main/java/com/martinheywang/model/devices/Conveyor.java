package com.martinheywang.model.devices;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.annotations.AccessibleName;
import com.martinheywang.model.devices.annotations.ActionCost;
import com.martinheywang.model.devices.annotations.Buildable;
import com.martinheywang.model.devices.annotations.DefaultTemplate;
import com.martinheywang.model.devices.annotations.Description;
import com.martinheywang.model.devices.annotations.Prices;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.templates.Template.PointerTypes;

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
	public final void act(Pack resources) throws MoneyException {
		if (this.gameManager.getMoney()
				.compareTo(this.getActionCost()) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}

		final Coordinate output = this.template
				.getPointersFor(PointerTypes.EXIT).get(0);

		if (this.gameManager.connectionExists(this.getPosition(), output)) {
			// Remove action cost
			this.gameManager.removeMoney(this.getActionCost());

			this.gameManager.performAction(this.getPosition(), output, resources);
		}
	}

}
