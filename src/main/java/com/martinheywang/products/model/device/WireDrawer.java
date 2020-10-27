package com.martinheywang.products.model.device;

import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.action.Action;
import com.martinheywang.products.model.device.annotation.AccessibleName;
import com.martinheywang.products.model.device.annotation.ActionCost;
import com.martinheywang.products.model.device.annotation.Buildable;
import com.martinheywang.products.model.device.annotation.DefaultTemplate;
import com.martinheywang.products.model.device.annotation.Description;
import com.martinheywang.products.model.device.annotation.Prices;
import com.martinheywang.products.model.exception.MoneyException;
import com.martinheywang.products.model.resource.DefaultResource;
import com.martinheywang.products.model.resource.Resource;
import com.martinheywang.products.model.resource.ToWire;
import com.martinheywang.products.model.template.Template.PointerType;

import javafx.scene.Node;

@Extension
@AccessibleName("Presse à fil")
@Description("Le presse à fil fond les resources qui lui parviennent en fils")
@Prices(build = "2000", upgradeTo2 = "15000", upgradeTo3 = "500000", destroyAt1 = "1800", destroyAt2 = "13000", destroyAt3 = "450000")
@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("8")
public class WireDrawer extends Device {

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
	action.setGivenResources(new Pack(transformed, resources.getQuantity()));
	action.addCost(this.getActionCost());
	action.markAsSuccessful();

	return action;
    }

    @Override
    public final List<Node> getWidgets() {
	return Arrays.asList();
    }
}