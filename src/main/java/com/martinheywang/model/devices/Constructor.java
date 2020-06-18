package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Constructor_;
import com.martinheywang.model.resources.BaseResources;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.GameController;
import com.martinheywang.view.Recipe;
import com.martinheywang.view.components.Carousel;
import com.martinheywang.view.components.Carousel.CarouselEvent;

import javafx.event.EventHandler;
import javafx.scene.Node;
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
		behaviour = new Constructor_(this, controller);
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

	@Override
	protected void initDashboard() {
		super.initDashboard();
		final Carousel carousel = new Carousel();
		Node selection = null;
		for (BaseResources res : BaseResources.values()) {
			Displayer<Resource> dis = new Displayer<Resource>(
					res.getDisplayer(), res);
			carousel.addNodes(dis);
			if (dis.getSubject().equals(this.getProduct())) {
				selection = dis;
			}
		}
		carousel.setSelection(selection);

		final VBox box = new VBox();
		final Label extensionTitle = new Label(
				"Changer la ressource distribuée :");

		final Label recipeTitle = new Label("Aperçu de la recette :");
		final Recipe recipeView = new Recipe(BaseResources.NONE);

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
		dashboard.addNode(box);
	}

	public void setProduct(Resource res) throws NullPointerException {
		if (behaviour instanceof Constructor_) {
			((Constructor_) behaviour).setProduit(new Pack(res, 1));
		}
	}

	public Resource getProduct() throws NullPointerException {
		if (behaviour instanceof Constructor_)
			return ((Constructor_) behaviour).getProduit()
					.getRessource();
		return null;
	}
}
