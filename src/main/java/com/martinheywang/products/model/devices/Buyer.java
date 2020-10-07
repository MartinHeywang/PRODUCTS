package com.martinheywang.products.model.devices;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.database.Database;
import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.ActionCost;
import com.martinheywang.products.model.devices.annotations.Buildable;
import com.martinheywang.products.model.devices.annotations.DefaultTemplate;
import com.martinheywang.products.model.devices.annotations.Description;
import com.martinheywang.products.model.devices.annotations.Prices;
import com.martinheywang.products.model.exceptions.MoneyException;
import com.martinheywang.products.model.resources.Buyable;
import com.martinheywang.products.model.resources.DefaultResource;
import com.martinheywang.products.model.resources.Resource;
import com.martinheywang.products.model.templates.Template.PointerTypes;
import com.martinheywang.products.view.Displayer;
import com.martinheywang.products.view.components.Carousel;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@Buildable
@ActionCost("5")
@AccessibleName("Acheteur")
@Description("Il achète les ressources et les envoit au prochain appareil.")
@DefaultTemplate(bottom = PointerTypes.EXIT)
@Prices(build = "500", destroyAt1 = "450", destroyAt2 = "9000", destroyAt3 = "140000", upgradeTo2 = "10000", upgradeTo3 = "150000")
public final class Buyer extends Device {

	/**
	 * <p>
	 * The list of accepted resources of this device.
	 * </p>
	 * <p>
	 * To add a Resource to this list you can either invoke
	 * {@link #addAcceptedResource(Resource)} or mark the declaration with
	 * {@link Buyable}.
	 * </p>
	 * <p>
	 * All the Resource must be marked with the {@link Buyable} annotation, but all
	 * Resources marked with Buyable <em>might be</em> in it.
	 * </p>
	 */
	private static final List<Resource> acceptedResources = new ArrayList<>();

	private Pack distributedResource;

	public Buyer(DeviceModel model) {
		super(model);

		// Buyers auto-activates
		Device.autoActiveDevices.add(this);

		loadDistributedResource();
	}

	/**
	 * Loads the distributed resource when the device is created
	 */
	private void loadDistributedResource() {
		try {
			final Dao<Pack, Long> dao = Database.createDao(Pack.class);

			// If the id is null (when just created), the id is null so it can't have a
			// associated pack already
			if (model.getID() == null)
				/*
				 * Here this exception is practical because it sets the packs without printing
				 * out the message
				 */
				throw new IndexOutOfBoundsException();

			final Pack fetched = dao.queryForEq("model", model.getID()).get(0);
			distributedResource = fetched;

		} catch (final SQLException e) {
			// An error with the database occured
			// contains any elements
			distributedResource = new Pack(DefaultResource.NONE, BigInteger.ONE, this.model);
			e.printStackTrace();
		} catch (final IndexOutOfBoundsException e) {
			// The fetched list doesn't contain any elements
			distributedResource = new Pack(DefaultResource.NONE, BigInteger.ONE, this.model);
		}
	}

	@Override
	public void act(Pack resource) throws MoneyException {
		super.act(resource);

		// Check if the resource is valid one (if the resource is buyable).
		if (!acceptedResources.contains(this.distributedResource.getResource())) {
			return;
		}

		// Here we retreive the price of the resource.
		final BigInteger resourceCost = new BigInteger(
				this.distributedResource.getResource().getField().getAnnotation(Buyable.class).price());

		// Here we check if we actually have enough money
		if (this.gameManager.getMoney().compareTo(resourceCost.add(this.getActionCost())) == -1) {
			// We don't have enough money (:sad-guy:)
			throw new MoneyException();
		}

		final Coordinate output = this.template.getPointersFor(PointerTypes.EXIT).get(0);

		if (this.gameManager.connectionExists(this.getPosition(), output)) {
			// Remove action cost
			this.gameManager.removeMoney(this.getActionCost().add(resourceCost));

			this.gameManager.performAction(this.getPosition(), output, this.distributedResource);
			this.setActive(true);
			this.setActive(false);
		}
	}

	@Override
	public List<Node> getWidgets() {
		final Carousel carousel = new Carousel();
		Node selection = null;

		carousel.addNodes(DefaultResource.NONE.getDisplayer());
		for (final Resource resource : acceptedResources) {
			final Displayer<Resource> display = resource.getDisplayer();
			carousel.addNodes(display);

			if (distributedResource.getResource() == resource) {
				selection = display;
			}
		}
		carousel.setSelection(selection);

		carousel.setOnSelectionChanged(event -> {
			@SuppressWarnings("unchecked")
			final Resource resource = ((Displayer<Resource>) event.getNewSelection()).getSubject();
			this.setDistributedResource(resource);
		});

		final VBox box = new VBox();
		box.setSpacing(2d);
		final Label helpLabel = new Label("Changer la ressource distribuée ici à l'aide des flèches:");
		helpLabel.setFont(new Font(11d));
		box.getChildren().addAll(helpLabel, carousel);

		return Arrays.asList(box);
	}

	@Override
	public void saveElements(){
		/* 
			<?> What is that ? Well here we don't change anything as far as I know, 
			this is necessary in order to save the pack.
		*/
		distributedResource.setModel(model);
		try {
			// Updates the distributed resource
			Database.createDao(Pack.class).createOrUpdate(distributedResource);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the distributed resource of the buyer.
	 * 
	 * @return the resource
	 */
	public Resource getDistributedResource() {
		return distributedResource.getResource();
	}

	/**
	 * Sets a new distributed resource to the device. <strong>Warning: If this
	 * resource is not a part of the accepted resources, this method won't warn you
	 * but the buyer will skip its action as it can't distributed it.</strong>
	 * 
	 * @param res the new resource
	 */
	public void setDistributedResource(Resource res) {
		distributedResource.setResource(res);
	}

	/**
	 * Adds a Resource to the accepted resources. Note that it must be marked with
	 * the Buyable annotation.
	 * 
	 * @param res the res to add
	 */
	public static final void addAcceptedResource(Resource res) {
		if (res.hasAnnotation(Buyable.class)) {
			acceptedResources.add(res);
		}
	}

	/**
	 * Removes an accepted resource, if it is in the list
	 * 
	 * @param res the res to remove
	 */
	public static final void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}

}
