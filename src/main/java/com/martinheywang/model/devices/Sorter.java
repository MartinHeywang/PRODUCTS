package com.martinheywang.model.devices;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.martinheywang.model.BaseResources;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.Resource;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.database.Saver;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.behaviours.Conveyor_;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.GameController;
import com.martinheywang.view.components.Carousel;
import com.martinheywang.view.components.Carousel.CarouselEvent;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Sorter extends Device {

	private Pack crit1, crit2;

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.EXIT,
			PointerTypes.EXIT);

	public Sorter(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		try {
			// Query for all the packages that are associated to this device
			final List<Pack> list = Database.createDao(Pack.class)
					.queryBuilder()
					.where().eq("device", model.getID()).query();
			// If its size equals 0, then create the resource and save it in the
			// database
			if (list.size() == 0) {
				crit1 = new Pack(BaseResources.NONE, 1, model);
				Saver.savePack(crit1);
				crit2 = new Pack(BaseResources.NONE, 1, model);
				Saver.savePack(crit2);
			}
			// Else we get at the first index the packing
			else {
				crit1 = list.get(0);
				crit2 = list.get(1);

			}

			// If the list is bigger than 2, there is an error (the resource was
			// added by the user (not in game)).
			// So the rest just doesn't matter
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());

		}

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Conveyor_(this, controller);

	}

	@Override
	public void action(Pack resATraiter) throws MoneyException {

		for (Coordinates xy : template.getPointersFor(PointerTypes.EXIT)) {
			if (xy.isInGrid(controller.getGridSize())) {
				final Device pointedDevice = controller.findDevice(xy);
				for (Coordinates enter : pointedDevice.getTemplate()
						.getPointersFor(PointerTypes.ENTRY)) {
					if (enter.getX() == model.getCoordinates().getX() &&
							enter.getY() == model.getCoordinates().getY()) {
						timeline.playFromStart();
						// Check for criterias to give the good pointer
						// First case (crit1), resource goes to the left
						if (resATraiter.getRessource()
								.equals(crit1.getRessource())) {
							behaviour.action(resATraiter,
									template.getPointersFor(PointerTypes.EXIT)
											.get(2));
							// Index 2 corresponds to the left because it should
							// be the last
							// element (we rotate clockwise)
						}
						// Second case (crit2), the resource goes to the right
						else if (resATraiter.getRessource()
								.equals(crit2.getRessource())) {
							behaviour.action(resATraiter,
									template.getPointersFor(PointerTypes.EXIT)
											.get(0));
							// Index 0 corresponds to the first element
						}
						// And dead in the center if there are no matching.
						else {
							behaviour.action(resATraiter,
									template.getPointersFor(PointerTypes.EXIT)
											.get(1));
							// Index 1 is the center
						}
					}
				}
			}
		}
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

	@Override
	protected void initDashboard() {
		super.initDashboard();
		Carousel carouselCrit1 = new Carousel();
		Carousel carouselCrit2 = new Carousel();
		Node selection1 = null;
		Node selection2 = null;
		for (Resource res : Resource.getReferences()) {
			Displayer<Resource> dis1 = new Displayer<Resource>(
					res.getDisplayer(), res);
			carouselCrit1.addNodes(dis1);
			if (dis1.getSubject().equals(this.getCriteria1())) {
				selection1 = dis1;
			}
			Displayer<Resource> dis2 = new Displayer<Resource>(
					res.getDisplayer(), res);
			carouselCrit2.addNodes(dis2);
			if (dis2.getSubject().equals(this.getCriteria2())) {
				selection2 = dis2;
			}

		}
		carouselCrit1.setSelection(selection1);
		carouselCrit2.setSelection(selection2);
		carouselCrit1.setOnSelectionChanged(new EventHandler<CarouselEvent>() {
			@Override
			public void handle(CarouselEvent event) {
				Resource res = (Resource) ((Displayer<?>) event
						.getNewSelection()).getSubject();
				setCriteria1(res);
			}
		});
		carouselCrit2.setOnSelectionChanged(new EventHandler<CarouselEvent>() {
			@Override
			public void handle(CarouselEvent event) {
				Resource res = (Resource) ((Displayer<?>) event
						.getNewSelection()).getSubject();
				setCriteria2(res);
			}
		});

		VBox first = new VBox();
		Label left = new Label(
				"Choissisez la ressource à diriger vers la gauche :");
		VBox second = new VBox();
		Label right = new Label(
				"Choissiez la ressource à diriger vers la droite :");
		Label center = new Label(
				"Les ressources ne correspondant pas à aucun des critères seront dirigées tout droit.");
		first.getChildren().addAll(left, carouselCrit1);
		second.getChildren().addAll(right, carouselCrit2);
		dashboard.addNode(center);
		dashboard.addNode(second);
		dashboard.addNode(first);
	}

	public void setCriteria1(Resource res) {
		this.crit1 = new Pack(res, 1);
	}

	public void setCriteria2(Resource res) {
		this.crit2 = new Pack(res, 1);
	}

	public Resource getCriteria1() {
		return crit1.getRessource();
	}

	public Resource getCriteria2() {
		return crit2.getRessource();
	}
}