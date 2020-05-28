package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.Packing;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Constructor_;
import com.martinheywang.view.Carousel;
import com.martinheywang.view.Carousel.CarouselEvent;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.GameController;
import com.martinheywang.view.Recipe;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Constructor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.NONE,
			PointerTypes.ENTRY);

	public Constructor(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Constructor_(model, controller);

		final Carousel carousel = new Carousel();
		for (Resource res : Resource.values()) {
			if (Constructor_.acceptedResources.contains(res))
				carousel.addNodes(
						new Displayer<Resource>(res.getDisplayer(), res));
		}

		final VBox box = new VBox();
		final Label extensionTitle = new Label(
				"Changer la ressource distribuée :");

		final Label recipeTitle = new Label("Aperçu de la recette :");
		final Recipe recipeView = new Recipe(Resource.NONE);

		carousel.setOnSelectionChanged(new EventHandler<CarouselEvent>() {
			@Override
			public void handle(CarouselEvent event) {
				Resource res = (Resource) ((Displayer<?>) event
						.getNewSelection()).getSubject();
				setProduit(res);
				recipeView.setDisplayedResource(res);
			}
		});
		box.getChildren().addAll(extensionTitle, carousel, recipeTitle,
				recipeView);
		dashboard.addNode(box);
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

	public void setProduit(Resource res) throws NullPointerException {
		if (behaviour instanceof Constructor_) {
			((Constructor_) behaviour).setProduit(new Packing(res, 1));
		}
	}

	public Resource getProduit() throws NullPointerException {
		if (behaviour instanceof Constructor_)
			return ((Constructor_) behaviour).getProduit()
					.getRessource();
		return null;
	}
}
