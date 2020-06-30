package com.martinheywang.model.devices;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.behaviours.Constructor_;
import com.martinheywang.model.resources.BaseResources;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.GameController;
import com.martinheywang.view.components.Carousel;
import com.martinheywang.view.components.Carousel.CarouselEvent;
import com.martinheywang.view.components.Recipe;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Constructor extends Device {

	public Constructor(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
		behaviour = new Constructor_(this, controller);
	}

	@Override
	protected List<Node> getWidgets() {

		final Carousel carousel = new Carousel();

		Node selection = null;

		final VBox box = new VBox();
		final Label extensionTitle = new Label(
				"Changer la ressource distribuée :");
		final Label recipeTitle = new Label("Aperçu de la recette :");
		final Recipe recipeView = new Recipe(BaseResources.NONE);

		for (Resource res : Constructor_.acceptedResources) {
			Displayer<Resource> dis = new Displayer<Resource>(
					res.getDisplayer(), res);
			carousel.addNodes(dis);
			if (dis.getSubject().equals(this.getProduct())) {
				selection = dis;
				recipeView.setDisplayedResource(res);
			}
		}

		carousel.setSelection(selection);

		carousel.setOnSelectionChanged(new EventHandler<CarouselEvent>() {
			@Override
			public void handle(CarouselEvent event) {
				BaseResources res = (BaseResources) ((Displayer<?>) event
						.getNewSelection()).getSubject();
				setProduct(res);
				recipeView.setDisplayedResource(res);
			}
		});

		box.getChildren().addAll(extensionTitle, carousel, recipeTitle,
				recipeView);

		return Arrays.asList(box);
	}

	public void setProduct(Resource res) throws NullPointerException {
		if (behaviour instanceof Constructor_) {
			((Constructor_) behaviour).setProduct(new Pack(res, 1));
		}
	}

	public Resource getProduct() throws NullPointerException {
		if (behaviour instanceof Constructor_)
			return ((Constructor_) behaviour).getProduct()
					.getResource();
		return null;
	}
}
