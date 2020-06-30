package com.martinheywang.model.devices;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.behaviours.Buyer_;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.GameController;
import com.martinheywang.view.components.Carousel;
import com.martinheywang.view.components.Carousel.CarouselEvent;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Buyer extends Device {

	public static ArrayList<Coordinate> locations = new ArrayList<Coordinate>();

	public Buyer(DeviceModel model, GameController controller)
			throws FileNotFoundException {

		super(model, controller);
		locations.add(model.getCoordinates());

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new Buyer_(this, controller);

	}

	@Override
	protected List<Node> getWidgets() {

		final VBox root = new VBox();
		final Carousel carousel = new Carousel();

		Node selection = null;

		for (Resource res : Buyer_.acceptedResources) {
			Displayer<Resource> dis = new Displayer<Resource>(
					res.getDisplayer(), res);
			carousel.addNodes(dis);
			if (dis.getSubject().equals(this.getDistributedResource())) {
				selection = dis;
			}
		}

		carousel.setSelection(selection);

		carousel.setOnSelectionChanged(new EventHandler<CarouselEvent>() {
			@Override
			public void handle(CarouselEvent event) {
				Resource res = (Resource) ((Displayer<?>) event
						.getNewSelection()).getSubject();
				setDistributedResource(res);
			}
		});

		final Label title = new Label("Modifier la ressource distribuée :");
		title.getStyleClass().add("h5");

		root.getChildren().addAll(title, carousel);

		return Arrays.asList(root);
	}

	/**
	 * 
	 * @return the distributed resource
	 * @throws NullPointerException if the behaviour of this device isn't
	 *                              a buyer
	 */
	public Resource getDistributedResource() throws NullPointerException {
		if (behaviour instanceof Buyer_)
			return ((Buyer_) behaviour)
					.getDistributedResource().getResource();
		return null;
	}

	/**
	 * 
	 * @param res the new value of the property
	 */
	public void setDistributedResource(Resource res) {
		if (behaviour instanceof Buyer_) {
			((Buyer_) behaviour)
					.setDistributedResource(new Pack(res, 1));
		}
	}
}