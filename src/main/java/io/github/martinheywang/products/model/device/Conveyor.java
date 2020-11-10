package io.github.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import javafx.scene.Node;

/**
 * A conveyor is a simple
 * {@link io.github.martinheywang.products.api.model.device.Device} that takes
 * the resource and give it simply to the next device without changing it in any
 * form.
 */
@Extension(ordinal = 3)
@AccessibleName("Convoyeur")
@Description("Le convoyeur transporte les ressources d'u point A à un point B")
@Prices(build = "100", upgradeTo2 = "5000", upgradeTo3 = "100000", destroyAt1 = "100", destroyAt2 = "5000", destroyAt3 = "100000")
@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("5")
public class Conveyor extends Device {

	/**
	 * Creates a new Conveyor
	 * 
	 * @param model a device model, wherez the type may be null.
	 */
	public Conveyor(DeviceModel model) {
		super(model);
	}

	@Override
	public final Action act(Pack resources) throws MoneyException {
		final Action action = new Action(this, resources);

		final Coordinate output = this.template.getPointersFor(PointerType.EXIT).get(0);
		action.setOutput(output);
		action.setGivenPack(resources);
		action.addCost(this.getActionCost());
		action.markAsSuccessful();

		return action;
	}

	@Override
	public List<Node> getWidgets() {
		return Arrays.asList();
	}

}
