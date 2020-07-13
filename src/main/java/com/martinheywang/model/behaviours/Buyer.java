package com.martinheywang.model.behaviours;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.resources.DefaultResources;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.GameController;
import com.martinheywang.view.components.Carousel;
import com.martinheywang.view.components.Carousel.CarouselEvent;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Martin Heywang
 *
 */
public class Buyer extends Behaviour {

	/**
	 * This {@link List} defines which Resource are accepted by this
	 * device and which are not. It has a set of default, but you can add
	 * and remove some by calling
	 * {@link Buyer#addAcceptedResource(Resource)} or
	 * {@link Buyer#removeAcceptedResource(Resource)}.
	 * 
	 * @see Resource
	 */
	public static List<Resource> acceptedResources = new ArrayList<>();

	private Pack distributedResource;

	public Buyer(Device device, GameController controller) {
		super(device, controller);

		distributedResource = new Pack(DefaultResources.NONE, 1,
				device.getModel());

		try {
			loadDistributedResource();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());

		}
	}

	/**
	 * Loads the distributed resource from the database or create one if
	 * none is found.
	 * 
	 * @throws SQLException if an error with the database occurs.
	 */
	private void loadDistributedResource() throws SQLException {
		// Todo : load resource
	}

	@Override
	public void action(Pack resATraiter)
			throws MoneyException {

		// distributedResource.setQuantity(0);
		//
		// for (int niveau = 0; niveau < this.level.getValue(); niveau++) {
		//
		// if (controller.getMoney()
		// .compareTo(BigInteger
		// .valueOf(5 + Device.getElectricity())) == -1)
		// throw new MoneyException();
		// else {
		// if (!distributedResource.getResource()
		// .equals(BaseResources.NONE)) {
		// distributedResource.addQuantity(1);
		// device.activate();
		// controller.removeMoney(
		// BigInteger.valueOf(5 + Device.getElectricity()));
		// controller.findDevice(pointer).action(distributedResource);
		// }
		// }
		// }

		try {
			final Coordinate exit = template.getPointersFor(PointerTypes.EXIT)
					.get(0);

			if (!exit.isInGrid(controller.getGridSize())) {
				return;
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("A buyer must have at least one exit.");
			e.printStackTrace();
		}

	}

	@Override
	public List<Node> getWidgets() {

		final VBox root = new VBox();
		final Carousel carousel = new Carousel();

		Node selection = null;

		for (Resource res : acceptedResources) {
			Displayer<Resource> dis = new Displayer<Resource>(
					res.getDisplayer(), res);
			carousel.addNodes(dis);
			if (dis.getSubject()
					.equals(this.getDistributedResource().getResource())) {
				selection = dis;
			}
		}

		carousel.setSelection(selection);

		carousel.setOnSelectionChanged(new EventHandler<CarouselEvent>() {
			@Override
			public void handle(CarouselEvent event) {
				Resource res = (Resource) ((Displayer<?>) event
						.getNewSelection()).getSubject();
				setDistributedResource(new Pack(res, 0));
			}
		});

		final Label title = new Label("Modifier la ressource distribuée :");
		title.getStyleClass().add("h5");

		root.getChildren().addAll(title, carousel);

		return Arrays.asList(root);
	}

	/**
	 * Sets the products to the new value, after checking if it is a valid
	 * resource.
	 * 
	 * @param produit the resource to set
	 */
	public void setDistributedResource(Pack pack) {
		if (acceptedResources.contains(pack.getResource())) {
			this.distributedResource.setResource(pack.getResource());

			this.distributedResource.setQuantity(pack.getQuantity());
		}

	}

	/**
	 * 
	 * @return distributedResource the distributed resource
	 */
	public Pack getDistributedResource() {
		return distributedResource;
	}

	/**
	 * Adds a Resource in the white list of accepted Resource for this
	 * device. This method is protected against adding multiple times the
	 * exact same {@link Resource}.
	 * 
	 * @param res the Resource to add
	 */
	public static void addAcceptedResource(Resource res) {
		if (!acceptedResources.contains(res))
			acceptedResources.add(res);

	}

	/**
	 * Removes of the white list the given Resource.
	 * 
	 * @param res
	 */
	public static void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}
}