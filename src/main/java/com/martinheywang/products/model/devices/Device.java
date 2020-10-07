package com.martinheywang.products.model.devices;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Game;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.devices.annotations.AccessibleName;
import com.martinheywang.products.model.devices.annotations.ActionCost;
import com.martinheywang.products.model.devices.annotations.Buildable;
import com.martinheywang.products.model.devices.annotations.DefaultTemplate;
import com.martinheywang.products.model.devices.annotations.Description;
import com.martinheywang.products.model.devices.annotations.Prices;
import com.martinheywang.products.model.direction.Direction;
import com.martinheywang.products.model.exceptions.EditException;
import com.martinheywang.products.model.exceptions.MoneyException;
import com.martinheywang.products.model.level.Level;
import com.martinheywang.products.model.mechanics.GameManager;
import com.martinheywang.products.model.templates.Template;
import com.martinheywang.products.model.templates.TemplateCreator;
import com.martinheywang.products.model.templates.Template.PointerTypes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * @author Martin Heywang
 */
@AccessibleName
@Description
@Prices(build = "0", upgradeTo2 = "0", upgradeTo3 = "0", destroyAt1 = "0", destroyAt2 = "0", destroyAt3 = "0")
@DefaultTemplate
@ActionCost
public abstract class Device {

    /**
     * This list represents all the Device that auto-activates each iterations of
     * the game loop. These are the entry point of the assembly line. What it does
     * must not depends on the 'resources' arg.
     */
    public static List<Device> autoActiveDevices = new ArrayList<>();
    public static List<Class<? extends Device>> subclasses = new ArrayList<>();

    // The model is the persistent data of the device
    protected final DeviceModel model;
    // The next is created each time we load the game
    protected GameManager gameManager;

    protected Template template;

    private final BooleanProperty activeProperty = new SimpleBooleanProperty(false);

    /* The view is updated on upgrade and on turn */
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    /**
     * The number of time that this device was used in the current iteration.
     */
    protected int currentIterationUse = 1;

    /**
     * The last time that device was used.
     */
    protected LocalDateTime lastUseTime = LocalDateTime.now();

    /**
     * Creates a new Device.
     * 
     * @param type      the type
     * @param direction the direction
     * @param level     the level
     * @param position  the position
     * @param game      the game
     */
    Device(DeviceModel model) {
        this.model = model;

        this.generateTemplate();

        this.refreshView();
    }

    /**
     * Defines the GameManager that this Device object will give to its behaviour to
     * perform actions.
     * 
     * @param gameManager
     */
    public void manageWith(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * <p>
     * Do whatever the device is meant to do when it is called by the previous
     * device in the assembly line. In most cases, you should call the next device
     * to keep the idea of an assembly line.
     * </p>
     * 
     * <p>
     * <strong>Note:</strong> If the device is registered in the auto active device
     * list, the 'resources' is always equal to null, so it the action musn't depend
     * on it.
     * </p>
     * 
     * @param resources the resource given by the previous device
     * @throws MoneyException whenever the game doesn't have enough money (don't
     *                        forget to check that)
     */
    public void act(Pack resources) throws MoneyException {
        final LocalDateTime now = LocalDateTime.now();
        final long between = ChronoUnit.MILLIS.between(lastUseTime, now);
        if (between < GameManager.gameLoopDelay) {
            currentIterationUse++;

            // Max use per iterations is the level times the entries count times 2
            if (currentIterationUse > this.getLevel().getValue() * this.getEntriesCount() * 2
                    && !this.getClass().equals(Buyer.class)) {
                return;
            }
        } else {
            currentIterationUse = 1;
        }
        lastUseTime = now;
    }

    /**
     * This inheritable method does not nothing by default. You don't need everytime
     * to inherit it. But in case your device uses savable packs, in this method you
     * should save these packs to make sure they are stored in the database.
     */
    public void saveElements() {
    }

    /**
     * Generates the template of the device
     */
    public void generateTemplate() {
        final DefaultTemplate annotation = this.getClass().getAnnotation(DefaultTemplate.class);
        this.template = TemplateCreator.getSingleton().setTop(annotation.top()).setRight(annotation.right())
                .setBottom(annotation.bottom()).setLeft(annotation.left()).getModel()
                .create(this.getPosition(), this.getDirection());
    }

    private void refreshView() {
        this.imageProperty.set(new Image(this.getClass().getResourceAsStream(this.getURL())));
    }

    public Template getTemplate() {
        return this.template;
    }

    /**
     * Parses the value of the @ActionCost annotation into a BigInteger, or return 5
     * if such an annotation isn't present.
     * 
     * @return the action cost
     */
    protected BigInteger getActionCost() {
        if (this.getClass().isAnnotationPresent(ActionCost.class)) {
            return new BigInteger(this.getClass().getAnnotation(ActionCost.class).value());
        }
        return new BigInteger("5");
    }

    /**
     * Returns a prices modules that get you the information you need about the
     * prices of this device type.
     * 
     * @return a prices modules
     */
    protected PricesModule getPrices() {
        if (this.getClass().isAnnotationPresent(Prices.class)) {
            final Prices annotation = this.getClass().getAnnotation(Prices.class);
            return new PricesModule(annotation.build(), annotation.upgradeTo2(), annotation.upgradeTo3(),
                    annotation.destroyAt1(), annotation.destroyAt2(), annotation.destroyAt3());
        }
        return new PricesModule("0", "0", "0", "0", "0", "0");
    }

    /**
     * 
     * @return the number of entries that this device has
     */
    public int getEntriesCount() {
        return this.template.getPointersFor(PointerTypes.ENTRY).size();
    }

    /**
     * 
     * @return the number of exits that this device has.
     */
    public int getExitsCount() {
        return this.template.getPointersFor(PointerTypes.EXIT).size();
    }

    public abstract List<Node> getWidgets();

    public String getURL() {
        return "/images" + this.getLevel().getURL() + this.getClass().getSimpleName().toUpperCase() + ".png";
    }

    /**
     * 
     * @return the model (persistent data)
     */
    public DeviceModel getModel() {
        return this.model;
    }

    /**
     * 
     * @return the level of this Device object.
     */
    public Level getLevel() {
        return this.model.getLevel();
    }

    /**
     * 
     * @return the game of this Device object.
     */
    public Game getGame() {
        return this.model.getGame();
    }

    /**
     * 
     * @return the direction of this Device object.
     */
    public Direction getDirection() {
        return this.model.getDirection();
    }

    /**
     * 
     * @return the position of this Device object.
     */
    public Coordinate getPosition() {
        return this.model.getPosition();
    }

    /**
     * Returns the view used by the device view.
     * 
     * @return the view
     */
    public ObjectProperty<Image> getView() {
        return this.imageProperty;
    }

    /**
     * Returns the price (or gain) of deleting this device.
     * 
     * @return a price as a BigInteger
     */
    public BigInteger getDeletePrice() {
        final String key = this.getDeletePriceKey();
        return this.getPrices().getPriceFromKey(key);
    }

    /**
     * Returns the price of upgrading this device.
     * 
     * @return a price as a BigInteger
     */
    public BigInteger getUpgradePrice() {
        final String key = this.getUpgradePriceKey();
        return this.getPrices().getPriceFromKey(key);
    }

    /*
     * The following methods (about keys) creates and returns keys to find the
     * appropriate prices in the #getPrices().
     */
    /**
     * Returns the valid delete price key for this device.
     * 
     * @return a string
     */
    private String getDeletePriceKey() {
        return this.getLevel().toString().toLowerCase() + "_delete";
    }

    /**
     * Returns the valid upgarde price key for this device.
     * 
     * @return a string
     */
    private String getUpgradePriceKey() {
        return this.getLevel().getNext().toString().toLowerCase() + "_build";
    }

    /**
     * Builds the given type on this device (replaces it), if all the condition are
     * valid.
     * 
     * @param type the class of the type to build
     * @throws EditException if we are not allowed to do something
     */
    public void build(Class<? extends Device> type) throws EditException {
        // Error checking
        if (!type.isAnnotationPresent(Buildable.class)) {
            /* If the type isn't buildable (as floors). */
            throw new EditException("The given type isn't buildable.");
        }
        if (!this.getClass().equals(Floor.class)) {
            /*
             * <?> Floor are normal devices, and it is the only type that can receive build.
             * Here we must check if we are effectively trying to build something on a
             * floor, and throw an exception if not.
             */
            this.gameManager.toast("Vous ne pouvez construire que sur des sols.", Color.DARKORANGE, 4d);
            return;
        }

        // Call the build method
        try {
            this.gameManager.build(type, this.getPosition());
        } catch (final MoneyException e) {
            this.gameManager.toast("Vous n'avez pas assez d'argent !", Color.ORANGERED, 4d);
        }
    }

    /**
     * Destroys the device and replace it with a floor, on which we can once again
     * build a Device.
     * 
     * @throws EditException
     */
    public void destroy() throws EditException {
        // Error checking
        if (this.getClass().equals(Floor.class)) {
            /* A floor cannot be destroyed */
            throw new EditException("A floor cannot be destroyed.");
        }

        try {
            this.gameManager.destroy(this.getPosition(), this.getLevel());
        } catch (final MoneyException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Swaps the device with the next device that call this method.
     * </p>
     * <p>
     * Register the device coords as 'want to swap' in the game manager and performs
     * the action
     * </p>
     * 
     * @see GameManager#swap(Coordinate)
     */
    public void swap() {
        this.gameManager.swap(this.getPosition());
    }

    /**
     * Turns the device properly.
     */
    public void turn() {
        this.model.setDirection(this.model.getDirection().getNext());

        this.generateTemplate();

        this.gameManager.refreshViewAt(this.getPosition());
    }

    /**
     * Upgrades the device properly.
     */
    public void upgrade() throws EditException {
        if (this.getClass().equals(Floor.class)) {
            throw new EditException();
        }

        final BigInteger actionCost = this.getUpgradePrice();

        try {
            this.gameManager.removeMoney(actionCost);
        } catch (final MoneyException e) {
            this.gameManager.toast("Vous n'avez pas assez d'argent! (" + actionCost + " € demandés)", Color.ORANGERED,
                    4d);
            return;
        }

        this.model.setLevel(this.model.getLevel().getNext());
        this.refreshView();

    }

    /**
     * 
     * @return the active property
     */
    public BooleanProperty activeProperty() {
        return this.activeProperty;
    }

    /**
     * @return the value of the active property
     */
    public boolean isActive() {
        return this.activeProperty.get();
    }

    /**
     * Sets the value of the property isActive
     * 
     * @param active the new active value
     */
    public void setActive(boolean active) {
        this.activeProperty.set(active);
    }

    public static void registerType(Class<? extends Device> clazz) {
        if (!subclasses.contains(clazz)) {
            subclasses.add(clazz);
        }
    }

}