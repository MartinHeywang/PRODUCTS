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
import com.martinheywang.products.model.template.Template.PointerType;

import javafx.scene.Node;

@Extension
@AccessibleName("Convoyeur")
@Description("Le convoyeur transporte les ressources d'u point A à un point B")
@Prices(build = "100", upgradeTo2 = "5000", upgradeTo3 = "100000", destroyAt1 = "100", destroyAt2 = "5000", destroyAt3 = "100000")
@DefaultTemplate(top = PointerType.ENTRY, bottom = PointerType.EXIT)
@Buildable
@ActionCost("5")
public class Conveyor extends Device {

    public Conveyor(DeviceModel model) {
	super(model);
    }

    @Override
    public final Action act(Pack resources) throws MoneyException {
	final Action action = new Action(this, resources);

	final Coordinate output = this.template.getPointersFor(PointerType.EXIT).get(0);
	action.setOutput(output);
	action.setGivenResources(resources);
	action.addCost(this.getActionCost());
	action.markAsSuccessful();

	return action;
    }

    @Override
    public List<Node> getWidgets() {
	return Arrays.asList();
    }

}
