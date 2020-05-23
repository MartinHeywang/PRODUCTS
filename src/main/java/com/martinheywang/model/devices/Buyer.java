package com.martinheywang.model.devices;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Packing;
import com.martinheywang.model.Resource;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Buyer_;
import com.martinheywang.view.GameController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Buyer extends Device {

	public static ArrayList<Coordinates> liste = new ArrayList<Coordinates>();
	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.NONE, PointerTypes.NONE, PointerTypes.EXIT,
			PointerTypes.NONE);

	public Buyer(DeviceModel model, GameController controller)
			throws FileNotFoundException {

		super(model, controller);
		liste.add(model.getCoordinates());

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Buyer_(model, controller);

		HBox changeResourceNode = new HBox();
		changeResourceNode.setMinHeight(130);
		changeResourceNode.setPadding(new Insets(5, 5, 5, 5));
		Button right = new Button("Resource suivante");
		Button left = new Button("Resource précédente");
		ImageView resourceView = new ImageView(
				new Image(getClass().getResourceAsStream(
						"/resources" + getDistributedResource().getURL())));
		changeResourceNode.getChildren().addAll(left, resourceView, right);
		changeResourceNode.setSpacing(10d);
		changeResourceNode.setAlignment(Pos.CENTER);
		HBox.setHgrow(resourceView, Priority.ALWAYS);
		dashboard.addNode(changeResourceNode);
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
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
					.getDistributedResource().getRessource();
		return null;
	}

	/**
	 * 
	 * @param res the new value of the property
	 */
	public void setDistributedResource(Resource res) {
		if (behaviour instanceof Buyer_) {
			((Buyer_) behaviour)
					.setDistributedResource(new Packing(res, 1));
		}
	}
}