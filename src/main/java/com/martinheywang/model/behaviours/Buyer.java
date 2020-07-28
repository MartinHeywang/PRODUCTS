package com.martinheywang.model.behaviours;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.mechanics.GameManager;
import com.martinheywang.model.resources.Buyable;
import com.martinheywang.model.resources.Ore;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.view.Displayer;
import com.martinheywang.view.components.Carousel;
import com.martinheywang.view.components.Carousel.CarouselEvent;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The Buyer is behaviour that buys the resources and send them to
 * next Device in the assembly line.
 * 
 * @author Martin Heywang
 */
public final class Buyer extends AbstractBehaviour {

	/**
	 * This {@link List} defines which Resource are accepted by this
	 * device and which are not. It has a set of default, but you can add
	 * and remove some by calling {@link Buyer#addAcceptedResource(Pack)}
	 * or {@link Buyer#removeAcceptedResource(Pack)}.
	 * 
	 * @see Resource
	 */
	public static List<Resource> acceptedResources = new ArrayList<>();

	private Pack distributedResource;

	public Buyer(Device device, GameManager gameManager) {
		super(device, gameManager);

		try {
			loadDistributedResource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the distributed resource from the database or create one if
	 * none is found.
	 * 
	 * @throws SQLException if an error with the database occurs.
	 */
	private void loadDistributedResource() throws SQLException {
		// Todo : load distributed resource
		distributedResource = new Pack(Ore.IRON, new BigInteger("1"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void act(Pack resATraiter)
			throws MoneyException {
		try {
			// Check if the resource is valid one (if the resource is buyable).
			if (!distributedResource.getResource()
					.hasAnnotation(Buyable.class)) {
				return;
			}
			/*
			 * <?> What the hell is that ?? If we are here, the distributed
			 * resource has already been checked (the resource is buyable). The
			 * line below fetches the cost of this resource using the same
			 * annotation.
			 */
			final BigInteger resourceCost = new BigInteger(
					distributedResource.getResource()
							.getField().getAnnotation(Buyable.class).price());

			// Here we check if we actually have enough money using the
			// compareTo method
			if (gameManager.getMoney()
					.compareTo(resourceCost.add(getActionCost())) == -1) {
				// We don't have enough money (:sad-guy:)
				throw new MoneyException();
			}

			final Coordinate output = this.template
					.getPointersFor(PointerTypes.ENTRY).get(0);

			// Call the next device.
			gameManager.performAction(getPosition(), output,
					distributedResource);

		} catch (IndexOutOfBoundsException e) {
			System.err.println("The Device at " + getPosition()
					+ " has no valid exit, but was asking for one. Skipping.");
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
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
				setDistributedResource(new Pack(res, new BigInteger("0")));
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