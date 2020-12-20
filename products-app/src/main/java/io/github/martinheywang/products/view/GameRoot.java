package io.github.martinheywang.products.view;

import java.util.List;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.controller.GameController;
import io.github.martinheywang.products.kit.view.controller.ContentRoot;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import io.github.martinheywang.products.view.component.DeviceView;
import io.github.martinheywang.products.view.component.GameGrid;
import io.github.martinheywang.products.view.component.GameStageBar;
import io.github.martinheywang.products.view.component.GameStatsView;
import io.github.martinheywang.products.view.component.LocatedScrollPane;
import io.github.martinheywang.products.view.component.MenuView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The GameRoot class extends {@link ContentRoot}. It is the base node of the
 * game view.
 */
public class GameRoot extends ContentRoot {

    private VBox base; // w/ the stage bar and the progress bar
    private HBox content; // w/o the stage bar

    private GameStageBar stageBar;
    private GameStatsView statsView;
    private GameGrid grid;
    private LocatedScrollPane scrollPane;
    private MenuView menuView;

    private GameController controller;

    public GameRoot(GameController controller) {
        super();

        this.getStylesheets().addAll(GameRoot.class.getResource("/css/Game.css").toExternalForm(),
                ViewUtils.class.getResource("/css/General.css").toExternalForm());

        this.controller = controller;

        this.base = new VBox();
        this.base.setPrefSize(900, 600);
        this.base.getStyleClass().add("contentBox");
        this.content = new HBox();

        this.stageBar = new GameStageBar();

        final Label title = new Label("PRODUCTS.");
        title.getStyleClass().addAll("title", "logo");
        this.stageBar.setTitle(title);

        this.grid = new GameGrid();
        this.scrollPane = new LocatedScrollPane(this.grid);
        this.menuView = new MenuView();

        this.statsView = new GameStatsView(controller);

        this.content.getChildren().addAll(this.scrollPane, this.menuView);
        this.base.getChildren().addAll(this.stageBar, this.statsView, this.content);

        VBox.setVgrow(this.content, Priority.ALWAYS);
        HBox.setHgrow(this.scrollPane, Priority.ALWAYS);

        setContent(base);
    }

    /**
     * Clears the game grid and refills it with the new views created based on the
     * given devices.
     * 
     * @param devices a list of device to display
     */
    public void feedWithDevices(List<Device> devices) {
        this.grid.getChildren().clear();
        for (Device device : devices) {
            final DeviceView view = new DeviceView(device, this.controller);
            this.grid.add(view, device.getPosition().getX(), device.getPosition().getY());
        }
    }

    /**
     * Refreshes the device view at the given position.
     * 
     * @param pos the position of the device view to refresh
     */
    public void refreshPos(Coordinate pos) {
        this.grid.refreshPos(pos);
    }

    /**
     * Pulses the view at the given coordinate position. More informations at :
     * {@link DeviceView#pulse()}.
     * 
     * @param pos the position where to pulse
     */
    public void pulsePos(Coordinate pos) {
        // TODO code mthd
    }

    /**
     * <p>
     * A toast is a simple message that displays the given node on the stage. You
     * add whatever you want.
     * </p>
     * <p>
     * This method creates the node for you and call {@link #toast(Node, double)}.
     * </p>
     * 
     * @param header   the header of the popup
     * @param content  the actual information
     * @param color    the side color of the toast
     * @param duration how long it will be shown in millis
     */
    public void toast(String header, String content, Color color, double duration) {
        // TODO code mthd
    }

    /**
     * Changes the whole view to the process mode, showing a progress bar, hiding
     * the rest. Disable almost all interactions for the user.
     */
    public void processMode() {
        // TODO code mthd
    }

    /**
     * Changes the whole view to the default mode. The default mode allows the user
     * to interact with the interface, the devices.
     */
    public void defaultMode() {
        // TODO code mthd
    }

    /**
     * Changes the whole view to the build mode. The build mode allows the user to
     * click on any device view to build a previously selected type of device.
     * Disable most of the other components to focus on the grid.
     */
    public void buildMode() {
        // TODO code mthd
    }
}