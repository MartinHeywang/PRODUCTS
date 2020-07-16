package com.martinheywang.model.devices;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.behaviours.Behaviour;
import com.martinheywang.model.behaviours.Buyer;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.direction.Direction;
import com.martinheywang.model.exceptions.EditException;
import com.martinheywang.model.exceptions.MoneyException;
import com.martinheywang.model.level.Level;
import com.martinheywang.model.types.BaseTypes;
import com.martinheywang.model.types.Type;
import com.martinheywang.toolbox.Tools;
import com.martinheywang.view.DeviceController;
import com.martinheywang.view.GameController;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

/**
 * <h3>A Device represents each square on the main game grid.</h3>
 * <p>
 * Each of them is different, it saves :
 * </p>
 * <ul>
 * <li>A game and its controller,</li>
 * <li>A coordinate,</li>
 * <li>A model,</li>
 * <li>A behaviour,</li>
 * <li>A template,</li>
 * <li>+ all the fields of
 * <code>javafx.scene.image.ImageView</code>,</li>
 * 
 * <li>and that's already enough...!</li>
 * </ul>
 * 
 * <h4>Game and Controller</h4>
 * <p>
 * A Device associates a Game on its constructor to difference it from
 * another Device with same Coordinate. Also, it has a
 * {@link GameController controller} from which it can destroy or
 * upgrade itself, or edit the money, for example. You should mess
 * around on your own with that property.
 * </p>
 * <h4>Coordinate</h4>
 * <p>
 * The Coordinate of a Device makes the difference between another
 * Device of the same game. Those two properties can be considered as
 * an identifier of the device. The behaviour, for example, will use
 * it a lot, to send data to near devices.
 * </p>
 * <h4>Model</h4>
 * <p>
 * The Model is the part of the Device that is stored in the database.
 * It contains all of the persistent data, such as the game and the
 * coordinate. But it has also three more properties : the Type, the
 * Level, and the Direction.
 * </p>
 * <h5>Type</h5>
 * <p>
 * Each Device of one partical Type has some common data. I can give
 * as an example the prices (build, delete, and whatsoever), his
 * behaviour class, his accesible name. {@link Type Here} you can find
 * the interface that manages Type.
 * </p>
 * <h5>Level</h5>
 * <p>
 * The level of the Device defines how efficient this Device is.
 * Basically, more the level is high, more it will do some actions
 * when it is called.
 * </p>
 * <h5>Direction</h5>
 * <p>
 * Of course, Device can also be turned (by right-clicking over it, by
 * the way). That's why they must save their direction, to be rebuild
 * the next time.
 * </p>
 * <h4>Behaviour</h4>
 * <p>
 * The Behaviour is obviously what this Device does. It depends and
 * changes according to the Type, that references a behaviour class.
 * </p>
 * <h4>Template</h4>
 * <p>
 * The Template defines where are the entrances and the exit of this
 * devices. (Resources enters by a entrances, Resources exits by an
 * exit). It depends on the Coordinate and the Direction, as it stores
 * the pointer of each exit and entrance. For example, you can say :
 * "Give me the list of coordinates from which the device can recieves
 * resources", and it will do it for you.
 * </p>
 * <p>
 * This type (in java-context) cannot be instantiated, it must be done
 * by the {@link TemplateModel}. In his own javadoc you will find how
 * to use it. (But the game already do it for you, so you won't have
 * to worry tooo much about it :)
 * </p>
 * 
 * @author Martin Heywang
 */
public class Device extends ImageView {

	/**
	 * The DeviceModel that contains all the data specified about this
	 * device. Persistent object.
	 * 
	 * @see DeviceModel
	 */
	protected DeviceModel model;

	/**
	 * The Behaviour is basically his behaviour that runs each iterations
	 * of the game loop.
	 * 
	 * @see GameController
	 * 
	 * @see Behaviour
	 */
	protected Behaviour behaviour;

	/**
	 * The controller is useful when you want to change somme data about
	 * the game.
	 * 
	 * @see GameController
	 */
	protected GameController controller;

	/**
	 * The template is a data object who gives all the pointers of this
	 * device, and can indicates which type of connection it is.
	 * 
	 * It is good practice to create first a private static TemplateModel
	 * :
	 * 
	 * <pre>
	 * <code>
	 * private static TemplateModel templateModel = new TemplateModel(PointerTypes.NONE, ...);
	 * </code>
	 * </pre>
	 * 
	 * Four values of type PointerTypes must be given to the contructor :
	 * four PointerTypes values that indicates which type of connection it
	 * is, respectively for the top, then right, then bottom, and to
	 * finish left. This way you can easily create the template, invoking
	 * {@link TemplateModel#createTemplate(Coordinate, Direction) this
	 * method} in the constructor of the device, like this :
	 * 
	 * <pre>
	 * <code>
	 * template = templateModel.createTemplate(model.getCoordinates(), model.getDirection());
	 * </code>
	 * </pre>
	 * 
	 * where <em>model</em> is the current model of the device.
	 * 
	 * <em>Here is an example with the conveyor :</em>
	 * 
	 * <pre>
	 * <code>
	 * // As a field
	 * private static final TemplateModel templateModel = 
	 * 	new TemplateModel(
	 * 		PointerTypes.ENTRY, 
	 * 		PointerTypes.NONE, 
	 * 		PointerTypes.EXIT, 
	 * 		PointerTypes.NONE);
	 * 		
	 *		
	 * // Later in the constructor
	 * template = templateModel.createTemplate(model.getCoordinates(),
	 *	model.getDirection());
	 *	
	 * </code>
	 * </pre>
	 * 
	 * @see Template
	 */
	protected Template template;

	/**
	 * This Timeline field represents the animation is invoked when his
	 * action is invoked. When overriding <code>Device.action</code>,
	 * don't forget do play this animation.
	 */
	protected Timeline timeline;

	/**
	 * What is did in case the user clicks on the Device.
	 */
	private EventHandler<MouseEvent> onClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				openDialog();
			} else if (event.getButton().equals(MouseButton.SECONDARY)) {
				try {
					controller.turn(model.getCoordinates());
				} catch (EditException e) {
					e.printStackTrace();
				}
			}
		}
	};

	// CONSTANTS TIME !!!
	public static int electricity = 5;
	public static final double defaultOpacity = .7d;
	// Opacity when a device is dragged but hasn't find a place yet
	public static final double cutOpacity = .4d;
	public static final double glowAmount = .4d;

	public static List<Coordinate> buyersLocations = new ArrayList<>();

	/**
	 * Creates a new device.
	 * 
	 * @param xy         the coordinates of this device
	 * @param type       the kind of the device
	 * @param direction  the rotate of this device
	 * @param niveau     the level of this device
	 * @param controller the game controller
	 * 
	 */
	public Device(DeviceModel model, GameController controller) {

		this.setOpacity(defaultOpacity);

		this.model = model;
		this.controller = controller;

		setData(model.getType(), model.getLevel(), model.getDirection());
	}

	private void openDialog() {
		try {
			final FXMLLoader loader = Tools.prepareFXMLLoader("Device");
			final Dialog<?> dialog = new Dialog<Void>();
			final DialogPane dialogPane;

			dialogPane = (DialogPane) loader.load();
			dialog.setTitle("Tableau de bord - PRODUCTS.");
			dialog.setDialogPane(dialogPane);
			dialog.initOwner(Main.getMainStage());
			dialog.initModality(Modality.NONE);

			final DeviceController controller = loader.getController();
			controller.setContent(this, dialog);
			controller.addActions(behaviour.getWidgets());

			dialog.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addHoverEffect() {
		this.setOnMouseEntered(
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						final Node hovered = (Node) event.getSource();
						Main.getMainStage().getScene()
								.setCursor(Cursor.HAND);
						hovered.setEffect(new Glow(glowAmount));
					}
				});
		this.setOnMouseExited(
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						final Node exited = (Node) event.getSource();
						Main.getMainStage().getScene()
								.setCursor(Cursor.DEFAULT);
						exited.setEffect(new Glow(0d));
					}
				});
	}

	/**
	 * This method represents the behaviour of this device. When called,
	 * it calls the defined behaviour.<br>
	 * <strong>Note :</strong> since most of the device has no longer more
	 * than 1 exit, this method is like a <em>shorcut</em> for <em>simple
	 * devices</em>.It only works if the devices has 1 exit, neither less
	 * or more. In those cases, this method <strong>must be</strong>
	 * overriden to make the device work properly.
	 * 
	 * @param resATraiter the resource who will be used by this device
	 */
	public void action(Pack resATraiter) throws MoneyException {
		behaviour.action(resATraiter);
	}

	/**
	 * Mark this device as activated. This action is only visual, it just
	 * make the device glowing a little bit.
	 */
	public void activate() {
		timeline.stop();
		timeline.playFromStart();
	}

	/**
	 * Returns the valid delete price key for this device.
	 * 
	 * @return a string
	 */
	public String getDeletePriceKey() {
		return this.model.getLevel().toString().toLowerCase() + "_delete";
	}

	/**
	 * Returns the valid upgarde price key for this device.
	 * 
	 * @return a string
	 */
	public String getUpgradePriceKey() {
		return this.model.getLevel().getNext().toString().toLowerCase()
				+ "_build";
	}

	/**
	 * Returns the price (or gain) of deleting this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getDeletePrice() {
		final String key = getDeletePriceKey();
		return this.model.getType().getPrices().getPriceFromKey(key);
	}

	/**
	 * Returns the price of upgrading this device.
	 * 
	 * @return a price as a BigInteger
	 */
	public BigInteger getUpgradePrice() {
		final String key = getUpgradePriceKey();
		return this.model.getType().getPrices().getPriceFromKey(key);
	}

	public DeviceModel getModel() {
		return model;
	}

	/**
	 * @return the level of this device
	 */
	public Level getLevel() {
		return model.getLevel();
	}

	/**
	 * 
	 * @return the direction of this device
	 */
	public Direction getDirection() {
		return model.getDirection();
	}

	/**
	 * 
	 * @return the type of this device
	 */
	public Type getType() {
		return model.getType();
	}

	/**
	 * 
	 * @param level the new level
	 */
	public void setLevel(Level level) {
		model.setLevel(level);

		this.setImage(new Image(
				getClass().getResourceAsStream(
						"/images" + model.getLevel().getURL()
								+ model.getType().getURL())));
	}

	/**
	 * 
	 * @param direction the new direction
	 */
	public void setDirection(Direction direction) {
		model.setDirection(direction);

		this.template = model.getType().getTemplateModel()
				.createTemplate(model.getCoordinates(), model.getDirection());

		this.setRotate(direction.getRotate());
	}

	/**
	 * 
	 * @param type the new type
	 */
	public void setType(Type type) {
		model.setType(type);

		try {

			this.setImage(new Image(
					getClass().getResourceAsStream(
							"/images" + model.getLevel().getURL()
									+ model.getType().getURL())));

			this.template = type.getTemplateModel()
					.createTemplate(model.getCoordinates(),
							model.getDirection());

			this.behaviour = type.getBehaviourClass()
					.getConstructor(Device.class, GameController.class)
					.newInstance(this, controller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setData(Type type, Level level, Direction direction) {
		try {
			Device.buyersLocations.remove(model.getCoordinates());

			model.setType(type);
			model.setLevel(level);
			model.setDirection(direction);

			this.setImage(new Image(
					getClass().getResourceAsStream(
							"/images" + model.getLevel().getURL()
									+ model.getType().getURL())));
			this.setRotate(direction.getRotate());

			this.template = type.getTemplateModel()
					.createTemplate(model.getCoordinates(),
							model.getDirection());

			this.behaviour = type.getBehaviourClass()
					.getConstructor(Device.class, GameController.class)
					.newInstance(this, controller);

			// We must reference Buyers in the list of coords
			if (behaviour.getClass().equals(Buyer.class)) {
				Device.buyersLocations.add(model.getCoordinates());
			}

			// Only floor doesn't have a click event
			if (!type.equals(BaseTypes.FLOOR)) {
				addHoverEffect();
				this.setOnMouseClicked(onClicked);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the comportement
	 */
	public Behaviour getBehaviour() {
		return behaviour;
	}

	/**
	 * @return the controller
	 */
	public GameController getController() {
		return controller;
	}

	/**
	 * 
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @return the electricity
	 */
	public static int getElectricity() {
		return electricity;
	}

	protected void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * @param electricity the electriciy to set
	 */
	public static void setElectricity(int electricity) {
		Device.electricity = electricity;
	}
}
