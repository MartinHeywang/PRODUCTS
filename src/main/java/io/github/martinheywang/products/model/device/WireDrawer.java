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
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.ToWire;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.model.resource.DefaultResource;
import javafx.scene.Node;

/**
 * A wire drawer transforms to wire the resource that he receives, - it it's
 * possible -. If not, the action won't be successful, the assembly line won't
 * give suite.
 */
@Extension(ordinal = 6)
@AccessibleName("Presse à fil")
@Description("Le presse à fil fond les resources qui lui parviennent en fils")
@Prices(build = "2000", upgradeTo2 = "15000", upgradeTo3 = "500000", destroyAt1 = "1800", destroyAt2 = "13000", destroyAt3 = "450000")
@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("20")
public class WireDrawer extends Device {

	/**
	 * Creates a new WireDrawer.
	 * 
	 * @param model a device model, where the type may be null.
	 */
	public WireDrawer(DeviceModel model) {
		super(model);
	}

	@Override
	public final Action act(Pack resources) throws MoneyException {
		final Action action = new Action(this, resources);

		// The given pack cannot be transformed (error catching)
		if (!resources.getResource().hasAnnotation(ToWire.class))
			return action;

		final ToWire annotation = resources.getResource().getField().getAnnotation(ToWire.class);
		Resource transformed;
		try {
			transformed = (Resource) annotation.clazz().getField(annotation.field()).get(null);
		} catch (final Exception e) {
			e.printStackTrace();
			transformed = DefaultResource.NONE;
		}
		final Coordinate output = this.template.getPointersFor(PointerType.EXIT).get(0);
		action.setOutput(output);
		action.setGivenPack(new Pack(transformed, resources.getQuantity()));
		action.addCost(this.getActionCost());
		action.markAsSuccessful();

		return action;
	}

	@Override
	public final List<Node> getWidgets() {
		return Arrays.asList();
	}
}