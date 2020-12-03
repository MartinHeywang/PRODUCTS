package io.github.martinheywang.products.view.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
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

        this.zoomProperty.addListener( (obs, oldVal, newVal) -> {
            node.getImage().applyZoom(newVal.doubleValue());
        });
    }

    /**
     * Does nothing, you can't add Node into that GameGrid. See
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

    public void setZoom(double amount){
        if(amount < .4d || amount > 5d){
            return;
        }
        zoomProperty.set(amount);
    }

    public void zoomOut(){
        setZoom(this.zoomProperty.get() - this.zoomFactor);
    }

    public void zoomIn(){
        setZoom(this.zoomProperty.get() + this.zoomFactor);
    }
}
