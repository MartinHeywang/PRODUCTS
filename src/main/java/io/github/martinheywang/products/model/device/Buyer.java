/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.model.device;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 

import com.j256.ormlite.dao.Dao;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Independent;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.resource.Buyable;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.view.component.Carousel;
import io.github.martinheywang.products.model.database.Database;
import io.github.martinheywang.products.model.resource.DefaultResource;
import io.github.martinheywang.products.view.component.ResourceView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * The buyer is an independent {@link io.github.martinheywang.products.api.model.device.Device} 
 * that take nothing as entry and buys the resource to distribute it.
 * 
 * @author Martin Heywang
 */
 
@Buildable
@Independent
@ActionCost("10")
@AccessibleName("Acheteur")
@Description("Il achète les ressources et les envoit au prochain appareil.")
@DefaultTemplate(bottom = PointerType.EXIT)
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
	 * All the Resource must be marked with the {@link Buyable} annotation, but
	 * all Resources marked with Buyable <em>might be</em> in it.
	 * </p>
	 */
	private static final List<Resource> acceptedResources = new ArrayList<>();

	private Pack distributedResource;
	private BigInteger resourceCost;

	/**
	 * Creates a new buyer with the given model.
	 * 
	 * @param model a device model, where the type may be null.
	 */
	public Buyer(DeviceModel model) {
		super(model);

		this.loadDistributedResource();
	}

	/**
	 * Loads the distributed resource when the device is created
	 */
	private void loadDistributedResource() {
		this.distributedResource = new Pack(DefaultResource.NONE,
				BigInteger.ONE, this.model);
		try {
			final Dao<Pack, Long> dao = Database.createDao(Pack.class);

			// If the id is null (when just created), the id is null so it can't
			// have a
			// associated pack already
			if (this.model.getID() == null)
				/*
				 * Here this exception is practical because it sets the packs
				 * without printing out the message
				 */
				throw new IndexOutOfBoundsException();

			final Pack fetched = dao.queryForEq("model", this.model.getID())
					.get(0);
			this.setDistributedResource(fetched.getResource());

		} catch (final SQLException e) {
			e.printStackTrace();
		} catch (final IndexOutOfBoundsException e) {
		}
	}

	@Override
	public Action act(Pack resource) throws MoneyException {
		final Action action = new Action(this, null);

		if (!acceptedResources.contains(this.distributedResource.getResource()))
			// Return early; the distributed resource isn't a valid one.
			return action;

		final Coordinate output = this.template.getPointersFor(PointerType.EXIT)
				.get(0);

		action.addCost(this.getActionCost().add(this.resourceCost));
		action.setGivenPack(new Pack(
				this.distributedResource.getResource(), BigInteger.ONE));
		action.setOutput(output);
		action.markAsSuccessful();
		return action;
	}

	@Override
	public List<Node> getWidgets() {
		final Carousel carousel = new Carousel();
		Node selection = null;

		carousel.addNodes(new ResourceView(DefaultResource.NONE));
		for (final Resource resource : acceptedResources) {
			final ResourceView view = new ResourceView(resource);
			carousel.addNodes(view);

			if (this.distributedResource.getResource() == resource)
				selection = view;
		}
		carousel.setSelection(selection);

		carousel.setOnSelectionChanged(event -> {
			final Resource resource = ((ResourceView) event.getNewSelection())
					.getResource();
			this.setDistributedResource(resource);
		});

		final VBox box = new VBox();
		box.setSpacing(2d);
		final Label helpLabel = new Label(
				"Changer la ressource distribuée ici à l'aide des flèches:");
		helpLabel.setFont(new Font(11d));
		box.getChildren().addAll(helpLabel, carousel);

		return Arrays.asList(box);
	}

	@Override
	public void saveElements() {
		/*
		 * <?> What is that ? Well here we don't change anything as far as I
		 * know, this is necessary in order to save the pack.
		 */
		this.distributedResource.setModel(this.model);
		try {
			// Updates the distributed resource
			Database.createDao(Pack.class)
			.createOrUpdate(this.distributedResource);
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
		return this.distributedResource.getResource();
	}

	/**
	 * Sets a new distributed resource to the device. <strong>Warning: If this
	 * resource is not a part of the accepted resources, this method won't warn
	 * you but the buyer will skip its action as it can't distributed
	 * it.</strong>
	 * 
	 * @param res
	 *            the new resource
	 */
	public void setDistributedResource(Resource res) {
		if (!acceptedResources.contains(res))
			// Return early; the given resource isn't distributable.
			return;

		this.distributedResource.setResource(res);
		this.distributedResource.setQuantity(BigInteger.ONE);
		this.resourceCost = new BigInteger(
				res.getField().getAnnotation(Buyable.class).price());
	}

	/**
	 * Adds a Resource to the accepted resources. Note that it must be marked
	 * with the Buyable annotation.
	 * 
	 * @param res
	 *            the res to add
	 */
	public static final void addAcceptedResource(Resource res) {
		if (res.hasAnnotation(Buyable.class))
			acceptedResources.add(res);
	}

	/**
	 * Removes an accepted resource, if it is in the list
	 * 
	 * @param res
	 *            the res to remove
	 */
	public static final void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}

}
