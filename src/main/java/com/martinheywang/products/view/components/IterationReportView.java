package com.martinheywang.products.view.components;

import java.util.List;

import com.martinheywang.products.Main;
import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.devices.IterationReport;
import com.martinheywang.products.toolbox.MoneyFormat;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

        final VBox statsContainer = new VBox();
        statsContainer.setSpacing(2d);

        final Label stats = new Label("Statistiques");
        stats.getStyleClass().add("h5");
        statsContainer.getChildren().add(stats);

        String[] infos = { "Coût total : " + MoneyFormat.getSingleton().format(report.getTotalCost()),
                "Nombre d'actions réalisées : " + report.getActCount(),
                "Nombre maximum d'actions réalisables : " + report.getMaxActCount() };

        for (String info : infos) {
            final Label label = new Label(info);
            statsContainer.getChildren().add(label);
        }

        if (report.isOverflowed()) {
            final Label label = new Label("L'appareil est surchargé ! Certaines resources sont perdues, patron !");
            label.getStyleClass().addAll("h6");
            label.setStyle("-fx-text-fill: #F0840E");
            this.getChildren().add(label);
        }

        this.getChildren().add(statsContainer);

        final HBox entriesExitsContainer = new HBox();
        final VBox entriesContainer = new VBox();
        final VBox exitsContainer = new VBox();

        if (report.getAllReceived().size() > 0) {
            final Label entries = new Label("Entrées");
            entries.getStyleClass().add("h5");
            entriesContainer.getChildren().add(entries);
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

                entriesContainer.getChildren().addAll(coordinate, view);
            }
        }

        if (report.getAllGiven().size() > 0) {
            final Label exits = new Label("Sorties");
            exits.getStyleClass().add("h5");
            exitsContainer.getChildren().add(exits);
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

                exitsContainer.getChildren().addAll(coordinate, view);
            }
        }

        entriesExitsContainer.setSpacing(15d);
        entriesExitsContainer.getChildren().addAll(entriesContainer, exitsContainer);
        HBox.setHgrow(entriesContainer, Priority.ALWAYS);
        HBox.setHgrow(exitsContainer, Priority.ALWAYS);
        this.getChildren().add(entriesExitsContainer);
        this.setSpacing(20d);

    }

    public void setReport(IterationReport report) {
        this.report = report;
        refreshView();
    }
}
