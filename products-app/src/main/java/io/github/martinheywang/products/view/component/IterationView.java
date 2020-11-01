package io.github.martinheywang.products.view.component;

import java.util.List;

import io.github.martinheywang.products.Main;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Iteration;
import io.github.martinheywang.products.api.toolbox.MoneyFormat;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class allows to display a IterationReport.
 */
public class IterationView extends VBox {

	public IterationView(Iteration data) {
		this.setSpacing(20d);
		this.getStylesheets().addAll(
				Main.class.getResource("/fxml/Buttons.css").toExternalForm(),
				Main.class.getResource("/fxml/Label.css").toExternalForm());

		this.setDisplayed(data);
	}

	private void setDisplayed(Iteration iteration) {
		this.getChildren().clear();

		final VBox statsContainer = new VBox();
		statsContainer.setSpacing(2d);

		final Label stats = new Label("Statistiques");
		stats.getStyleClass().add("h5");
		statsContainer.getChildren().add(stats);

		final String[] infos = {
				"Coût total : " + MoneyFormat.getSingleton()
				.format(iteration.getCost()),
				"Nombre d'actions réalisées : " + iteration.getActCount(),
				"Nombre maximum d'actions réalisables : "
						+ iteration.getDevice().getMaxActCount()};

		for (final String info : infos) {
			final Label label = new Label(info);
			statsContainer.getChildren().add(label);
		}

		if (iteration.getDevice().isOverload()) {
			final Label label = new Label(
					"L'appareil est surchargé ! Certaines resources sont perdues, patron !");
			label.getStyleClass().addAll("h6");
			label.setStyle("-fx-text-fill: #F0840E");
			this.getChildren().add(label);
		}

		this.getChildren().add(statsContainer);

		final HBox entriesExitsContainer = new HBox();
		final VBox entriesContainer = new VBox();
		final FlowPane entriesPacksContainer = new FlowPane();
		entriesContainer.getChildren().add(entriesPacksContainer);
		final VBox exitsContainer = new VBox();
		final FlowPane exitsPacksContainer = new FlowPane();
		exitsContainer.getChildren().add(exitsPacksContainer);

		final List<Pack> receivedResources = iteration.getReceivedResources();
		if (receivedResources.size() > 0) {
			final Label entries = new Label("Entrées");
			entries.getStyleClass().add("h5");
			entriesContainer.getChildren().add(0, entries);

			for (final Pack pack : receivedResources) {
				final PackView view = new PackView(pack);
				entriesPacksContainer.getChildren().add(view);
			}
		}

		final List<Pack> givenResources = iteration.getGivenResources();
		if (givenResources.size() > 0) {
			final Label exits = new Label("Sorties");
			exits.getStyleClass().add("h5");
			exitsContainer.getChildren().add(0, exits);

			for (final Pack pack : givenResources) {
				final PackView view = new PackView(pack);
				exitsPacksContainer.getChildren().add(view);
			}
		}

		if (!entriesPacksContainer.getChildren().isEmpty())
			entriesExitsContainer.getChildren().add(entriesContainer);

		if (!exitsPacksContainer.getChildren().isEmpty())
			entriesExitsContainer.getChildren().add(exitsContainer);

		entriesExitsContainer.setSpacing(15d);
		this.getChildren().add(entriesExitsContainer);

		final Button refresh = new Button("Rafraîchir");
		refresh.setOnMousePressed(event -> this
				.setDisplayed(iteration.getDevice().getCurrentIteration()));
		this.getChildren().add(refresh);

	}
}
