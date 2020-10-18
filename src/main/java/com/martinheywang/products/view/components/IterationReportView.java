package com.martinheywang.products.view.components;

import java.util.List;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.devices.IterationReport;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class allows to display a IterationReport.
 */
public class IterationReportView extends VBox {

    private IterationReport report;

    public IterationReportView(IterationReport report) {
        this.report = report;
        this.getStylesheets().add(Main.class.getResource("/fxml/Label.css").toExternalForm());
        refreshView();
    }

    private void refreshView() {
        this.getChildren().clear();

        if (report.getAllReceived().size() > 0) {
            final Label entries = new Label("Entrées");
            entries.getStyleClass().add("h5");
            this.getChildren().add(entries);
        }

        for (Coordinate key : report.getAllReceived().keySet()) {
            final List<Pack> packs = report.getReceivedFrom(key);
            final HBox view = new HBox();
            final Label coordinate = new Label("Entrée " + key + " :");
            coordinate.getStyleClass().add("h6");

            if (!packs.isEmpty()) {
                for (Pack pack : packs) {
                    view.getChildren().add(pack.getDisplayer());
                }

                this.getChildren().addAll(coordinate, view);
            }
        }

        if (report.getAllGiven().size() > 0) {
            final Label exits = new Label("Sorties");
            exits.getStyleClass().add("h5");
            this.getChildren().add(exits);
        }
        for (Coordinate key : report.getAllGiven().keySet()) {
            final List<Pack> packs = report.getGivenTo(key);
            final HBox view = new HBox();
            final Label coordinate = new Label("Sortie " + key + " :");
            coordinate.getStyleClass().add("h6");

            if (!packs.isEmpty()) {
                for (Pack pack : packs) {
                    view.getChildren().add(pack.getDisplayer());
                }

                this.getChildren().addAll(coordinate, view);
            }
        }

    }

    public void setReport(IterationReport report) {
        this.report = report;
        refreshView();
    }
}
