package io.github.martinheywang.products.kit.view.component;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * A LocatedScrollbar is a kind of progress bar, which is adapted to fit the
 * need of the game. Note that it doesn't support the indefinite progress thing.
 */
public final class LocatedProgressBar extends AnchorPane {

    private DoubleProperty progress;

    private final GridPane track;
    private final AnchorPane bar;

    private final ColumnConstraints barCol;
    private final ColumnConstraints voidCol;
    private final RowConstraints row;

    /**
     * Creates a new located progress bar.
     */
    public LocatedProgressBar() {
        this.track = new GridPane();
        this.track.getStyleClass().add("track");
        this.bar = new AnchorPane();
        this.bar.getStyleClass().addAll("bar", "cta");
        this.getStyleClass().add("located-progress-bar");
        this.progress = new SimpleDoubleProperty(0d);

        // GRID CONSTRAINTS
        this.barCol = new ColumnConstraints();
        this.barCol.percentWidthProperty().bind(progress);
        this.barCol.setFillWidth(true);

        this.voidCol = new ColumnConstraints();
        this.voidCol.percentWidthProperty().bind(new SimpleDoubleProperty(100d).subtract(progress));
        this.voidCol.setFillWidth(true);

        this.track.getColumnConstraints().addAll(barCol, voidCol);
        this.row = new RowConstraints();
        this.row.setPercentHeight(100d);
        this.track.getRowConstraints().add(row);

        this.track.add(bar, 0, 0);
        this.track.setPrefHeight(15d);
        this.track.setPadding(new Insets(0d));

        this.getChildren().add(track);
        this.setPadding(new Insets(0d));
        AnchorPane.setTopAnchor(track, 0d);
        AnchorPane.setRightAnchor(track, 0d);
        AnchorPane.setBottomAnchor(track, 0d);
        AnchorPane.setLeftAnchor(track, 0d);
    }

    /**
     * @return the double property
     */
    public DoubleProperty progressProperty() {
        return this.progress;
    }

    /**
     * @return the value of the progress property
     */
    public double getProgress() {
        return this.progress.get();
    }

    /**
     * Sets the value of the progress property.
     * 
     * @param value the new value
     */
    public void setProgress(double value) {
        this.progress.set(value * 100d);
    }
}
