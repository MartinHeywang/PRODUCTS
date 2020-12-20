package io.github.martinheywang.products.view.component;

import java.util.ArrayList;
import java.util.List;

import io.github.martinheywang.products.api.model.Coordinate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * <p>
 * The grid game component.
 * </p>
 * <p>
 * Thanks to
 * https://stackoverflow.com/questions/39827911/javafx-8-scaling-zooming-scrollpane-relative-to-mouse-position
 * </p>
 * 
 * @author Daniel Hári, Martin Heywang
 */
public class GameGrid extends GridPane {

    private DoubleProperty zoomProperty = new SimpleDoubleProperty();
    private List<DeviceView> children = new ArrayList<>();

    private final double zoomFactor = 0.05;

    public GameGrid() {
        super();

        setZoom(1d);
    }

    /**
     * Adds a DeviceView at the given coordinate.
     * 
     * @param node the DeviceView to add.
     * @param x    the column index
     * @param y    the row index
     */
    public void add(DeviceView node, int x, int y) {
        super.add(node, x, y);
        children.add(node);

        this.zoomProperty.addListener((obs, oldVal, newVal) -> {
            node.applyZoom(newVal.doubleValue());
        });
    }

    /**
     * Does nothing, you can't add basic Node into that GameGrid. See
     * {@link #add(DeviceView, int, int)}.
     * 
     * @param children    the children
     * @param columnIndex the column index
     * @param rowIndex    the row index
     */
    @Override
    public void add(Node children, int columnIndex, int rowIndex) {
        // Can't add basic node, only DeviceView(s)
        return;
    }

    /**
     * Returns the children of the grid, or at least, only the device view(s).
     * 
     * @return the childrens
     */
    public List<DeviceView> getDeviceChildren() {
        return children;
    }

    /**
     * Returns the view located at the given position (the one that display a device
     * with the given coordinate).
     * 
     * @param pos the position
     * @return the view if one was found, else null.
     */
    public DeviceView getView(Coordinate pos) {
        for (DeviceView view : getDeviceChildren()) {
            if (view.getDevice().getPosition().propertiesEquals(pos)) {
                return view;
            }
        }
        return null;
    }

    /**
     * Refreshes the device view at the given position.
     * 
     * @param pos the position of the device view to refresh
     */
    public void refreshPos(Coordinate pos) {
        this.getView(pos).refresh();
    }

    /**
     * Sets the new zoom amount
     * 
     * @param amount the new value
     */
    public void setZoom(double amount) {
        if (amount < .4d || amount > 5d) {
            return;
        }
        zoomProperty.set(amount);
    }

    /**
     * Performs a zoom out.
     */
    public void zoomOut() {
        setZoom(this.zoomProperty.get() - this.zoomFactor);
    }

    /**
     * Performs a zoom in.
     */
    public void zoomIn() {
        setZoom(this.zoomProperty.get() + this.zoomFactor);
    }
}
