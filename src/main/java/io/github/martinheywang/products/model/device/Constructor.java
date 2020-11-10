package io.github.martinheywang.products.model.device;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pf4j.Extension;

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
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.resource.Craftable;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.view.component.Carousel;
import io.github.martinheywang.products.controller.ResourceManager;
import io.github.martinheywang.products.model.database.Database;
import io.github.martinheywang.products.model.resource.DefaultResource;
import io.github.martinheywang.products.view.component.RecipeView;
import io.github.martinheywang.products.view.component.ResourceView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * A constructor is a complex
 * {@link io.github.martinheywang.products.api.model.device.Device} that takes
 * multiple entries but one exit, and create valuable products based on
 * different resources.
 */
@Extension(ordinal = 7)
@Buildable
@ActionCost("50")
@AccessibleName("Constructeur")
@Description("Crée des produits à partir de ressources primaires ou d'autres produits.")
@DefaultTemplate(top = PointerType.ENTRY, right = PointerType.EXIT, left = PointerType.ENTRY)
@Prices(build = "20000", upgradeTo2 = "75000", upgradeTo3 = "300000", destroyAt1 = "17500", destroyAt2 = "60000", destroyAt3 = "275000")
public final class Constructor extends Device {

	private static final List<Resource> acceptedResources = new ArrayList<>();
	private final List<Resource> availableResources = new ArrayList<>();
	private final List<Resource> extractedRecipe = new ArrayList<>();

	private Pack product;

	/**
	 * Creates a new Constructor with default values.
	 * 
	 * @param model a device model, where the type may be null.
	 */
	public Constructor(final DeviceModel model) {
		super(model);

		this.loadProduct();
	}

	private void loadProduct() {
		try {
			final Dao<Pack, Long> dao = Database.createDao(Pack.class);

			// If the id is null (when just created), the id is null so it can't
			// have a
			// associated pack already
			if (this.model.getID() == null)
				/*
				 * Here this exception is practical because it sets the packs without printing
				 * out the message
				 */
				throw new IndexOutOfBoundsException();

			final Pack fetched = dao.queryForEq("model", this.model.getID()).get(0);
			this.product = fetched;
			this.product.setQuantity(BigInteger.ONE);

		} catch (final SQLException e) {
			// An error with the database occured
			// contains any elements
			this.product = new Pack(DefaultResource.NONE, BigInteger.ONE, this.model);
			e.printStackTrace();
		} catch (final IndexOutOfBoundsException e) {
			// The fetched list doesn't contain any elements
			this.product = new Pack(DefaultResource.NONE, BigInteger.ONE, this.model);
		}
		this.generateExtractedRecipe();
	}

	private void generateExtractedRecipe() {
		this.extractedRecipe.clear();
		try {
			if (!this.product.getResource().equals(DefaultResource.NONE)) {

				final Craftable annotation = this.product.getResource().getClass()
						.getField(this.product.getResource().toString()).getAnnotation(Craftable.class);
				for (final Pack pack : ResourceManager.toPack(annotation.recipe()))
					for (BigInteger i = BigInteger.ZERO; i.compareTo(pack.getQuantity()) == -1; i = i
							.add(BigInteger.ONE))
						this.extractedRecipe.add(pack.getResource());
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Action act(final Pack resource) throws MoneyException {
		final Action action = new Action(this, resource);

		this.addResources(resource);
		action.addCost(this.getActionCost());
		action.markAsSuccessful();

		if (this.checkIngredients()) {
			final Coordinate output = this.template.getPointersFor(PointerType.EXIT).get(0);
			action.setOutput(output);
			action.setGivenPack(this.product);
		}

		return action;
	}

	/**
	 * Unpack the given packs and add it to the availableResources list.
	 * 
	 * @param packs the packs to 'unpack'
	 */
	private void addResources(final Pack... packs) {
		for (final Pack pack : packs)
			for (BigInteger i = BigInteger.ZERO; i.compareTo(pack.getQuantity()) == -1; i = i.add(BigInteger.ONE))
				this.availableResources.add(pack.getResource());
		for (int i = 0; this.availableResources.size() > 30; i++)
			this.availableResources.remove(i);
	}

	/**
	 * THis method act as a modified 'containsAll()'. It checks if the storage
	 * contains each of the resources that are needed to create the product.
	 * 
	 * @return true - if the resources are available
	 */
	private boolean checkIngredients() {
		if (this.extractedRecipe.isEmpty())
			// This case should happen only if the resource is either NONE, or
			// not craftable
			return false;

		final List<Resource> tempo = new ArrayList<>();
		for (final Resource resource : this.extractedRecipe)
			if (this.availableResources.contains(resource)) {
				tempo.add(resource);
				this.availableResources.remove(resource);
			} else {
				this.availableResources.addAll(tempo);
				tempo.clear();
				return false;
			}
		return true;
	}

	@Override
	public List<Node> getWidgets() {
		final Carousel carousel = new Carousel();
		final RecipeView recipeView = new RecipeView(this.product.getResource());

		Node selection = null;

		carousel.addNodes(new ResourceView(DefaultResource.NONE));
		for (final Resource resource : acceptedResources) {
			final ResourceView view = new ResourceView(resource);
			carousel.addNodes(view);

			if (this.product.getResource() == resource)
				selection = view;
		}
		carousel.setSelection(selection);

		carousel.setOnSelectionChanged(event -> {
			final Resource resource = ((ResourceView) event.getNewSelection()).getResource();
			this.setProduct(resource);
			recipeView.setDisplayedResource(resource);
		});

		final VBox node = new VBox();
		final Label label = new Label("Choissisez une ressource à produire:");
		label.setFont(new Font(10d));

		node.getChildren().addAll(label, carousel, recipeView);

		return Arrays.asList(node);
	}

	@Override
	public void saveElements() {
		this.product.setModel(this.model);
		try {
			// Updates the product
			Database.createDao(Pack.class).createOrUpdate(this.product);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the product of the constructor.
	 * 
	 * @return the resource
	 */
	public Resource getProduct() {
		return this.product.getResource();
	}

	/**
	 * Sets a new distributed resource to the device. <strong>Warning: If this
	 * resource is not a part of the accepted resources, this method won't warn you
	 * but the constructor will skip its action as it can't distributed it.</strong>
	 * 
	 * @param res the new resource
	 */
	public void setProduct(final Resource res) {
		this.product.setResource(res);
		try {
			// Updates the distributed resource
			Database.createDao(Pack.class).createOrUpdate(this.product);
			this.generateExtractedRecipe();
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds a Resource to the accepted resources. Note that it must be marked with
	 * the Craftable annotation.
	 * 
	 * @param res the res to add
	 */
	public static final void addAcceptedResource(Resource res) {
		if (res.hasAnnotation(Craftable.class))
			acceptedResources.add(res);
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
