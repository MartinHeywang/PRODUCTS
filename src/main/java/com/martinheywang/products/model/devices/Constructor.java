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
import com.martinheywang.products.model.resources.Craftable;
import com.martinheywang.products.model.resources.DefaultResource;
import com.martinheywang.products.model.resources.Resource;
import com.martinheywang.products.model.templates.Template.PointerTypes;
import com.martinheywang.products.view.Displayer;
import com.martinheywang.products.view.components.Carousel;
import com.martinheywang.products.view.components.RecipeView;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@Buildable
@ActionCost("15")
@AccessibleName("Constructeur")
@Description("Crée des produits à partir de ressources primaires ou d'autres produits.")
@DefaultTemplate(top = PointerTypes.ENTRY, right = PointerTypes.EXIT, left = PointerTypes.ENTRY)
@Prices(build = "20000", upgradeTo2 = "75000", upgradeTo3 = "300000", destroyAt1 = "17500", destroyAt2 = "60000", destroyAt3 = "275000")
public final class Constructor extends Device {

    private static final List<Resource> acceptedResources = new ArrayList<>();
    private final List<Resource> availableResources = new ArrayList<>();
    private final List<Resource> extractedRecipe = new ArrayList<>();

    private Pack product;

    public Constructor(final DeviceModel model) {
        super(model);

        loadProduct();
    }

    private void loadProduct() {
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
            product = fetched;

        } catch (final SQLException e) {
            // An error with the database occured
            // contains any elements
            product = new Pack(DefaultResource.NONE, BigInteger.ONE, this.model);
            e.printStackTrace();
        } catch (final IndexOutOfBoundsException e) {
            // The fetched list doesn't contain any elements
            product = new Pack(DefaultResource.NONE, BigInteger.ONE, this.model);
        }
        generateExtractedRecipe();
    }

    private void generateExtractedRecipe() {
        extractedRecipe.clear();
        try {
            if (!product.getResource().equals(DefaultResource.NONE)) {

                Craftable annotation = product.getResource().getClass().getField(product.getResource().toString())
                        .getAnnotation(Craftable.class);
                for (final Pack pack : Pack.toPack(annotation.recipe())) {
                    for (BigInteger i = BigInteger.ZERO; i.compareTo(pack.getQuantity()) == -1; i = i
                            .add(BigInteger.ONE)) {
                        extractedRecipe.add(pack.getResource());
                    }
                }
            }
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void act(final Pack resource) throws MoneyException {
        super.act(resource);
        addResources(resource);
        if (checkIngredients()) {
            final Coordinate output = this.template.getPointersFor(PointerTypes.EXIT).get(0);

            if (this.gameManager.connectionExists(this.getPosition(), output)) {
                // Remove action cost
                this.gameManager.removeMoney(this.getActionCost());

                this.gameManager.performAction(this.getPosition(), output, this.product);
                this.setActive(true);
                this.setActive(false);
            }
        }
    }

    /**
     * Unpack the given packs and add it to the availableResources list.
     * 
     * @param packs the packs to 'unpack'
     */
    private void addResources(final Pack... packs) {
        for (final Pack pack : packs) {
            for (BigInteger i = BigInteger.ZERO; i.compareTo(pack.getQuantity()) == -1; i = i.add(BigInteger.ONE)) {
                availableResources.add(pack.getResource());
            }
        }
        for (int i = 0; availableResources.size() > 30; i++) {
            availableResources.remove(i);
        }
    }

    /**
     * THis method act as a modified 'containsAll()'. It checks if the storage
     * contains each of the resources that are needed to create the product.
     * 
     * @return true - if the resources are available
     */
    private boolean checkIngredients() {
        if (extractedRecipe.isEmpty())
            // This case should happen only if the resource is either NONE, or not craftable
            return false;

        final List<Resource> tempo = new ArrayList<>();
        for (final Resource resource : extractedRecipe) {
            if (availableResources.contains(resource)) {
                tempo.add(resource);
                availableResources.remove(resource);
            } else {
                availableResources.addAll(tempo);
                tempo.clear();
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Node> getWidgets() {
        final Carousel carousel = new Carousel();
        final RecipeView recipeView = new RecipeView(product.getResource());

        Node selection = null;

        carousel.addNodes(DefaultResource.NONE.getDisplayer());
        for (final Resource resource : acceptedResources) {
            final Displayer<Resource> display = resource.getDisplayer();
            carousel.addNodes(display);

            if (product.getResource() == resource) {
                selection = display;
            }
        }
        carousel.setSelection(selection);

        carousel.setOnSelectionChanged(event -> {
            @SuppressWarnings("unchecked")
            final Resource resource = ((Displayer<Resource>) event.getNewSelection()).getSubject();
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
        product.setModel(model);
        try {
            // Updates the product
            Database.createDao(Pack.class).createOrUpdate(product);
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
        return product.getResource();
    }

    /**
     * Sets a new distributed resource to the device. <strong>Warning: If this
     * resource is not a part of the accepted resources, this method won't warn you
     * but the constructor will skip its action as it can't distributed it.</strong>
     * 
     * @param res the new resource
     */
    public void setProduct(final Resource res) {
        product.setResource(res);
        try {
            // Updates the distributed resource
            Database.createDao(Pack.class).createOrUpdate(product);
            generateExtractedRecipe();
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
        if (res.hasAnnotation(Craftable.class)) {
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
