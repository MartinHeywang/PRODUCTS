package io.github.martinheywang.products.view.component;

import java.time.format.DateTimeFormatter;

import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.toolbox.MoneyFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public final class GameView extends VBox {

	public GameView(Game data) {
		this.setPadding(new Insets(5));

		this.setDisplayed(data);
	}

	public void setDisplayed(Game game) {
		this.getChildren().clear();

		final Label nom = new Label();
		nom.setUnderline(true);
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(game.getName());
		nom.setWrapText(true);

		final DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("dd/MM/yyyy HH:mm");

		final Label money = new Label();
		money.setText("Argent en compte: "
				+ MoneyFormat.getSingleton().format(game.getMoney()));

		final Label save = new Label();
		save.setText(
				"Dernière sauvegarde: " + game.getLastSave().format(formatter));

		final Pane fancy = new Pane();
		fancy.setPrefHeight(2d);

		this.setOnMouseEntered(event -> {
			fancy.setBackground(new Background(new BackgroundFill(
					Color.DODGERBLUE, CornerRadii.EMPTY, new Insets(0))));
			this.setBackground(new Background(new BackgroundFill(
					Color.web("181818"), new CornerRadii(5d), new Insets(0d))));
		});
		this.setOnMouseExited(event -> {
			fancy.setBackground(Background.EMPTY);
			this.setBackground(Background.EMPTY);
		});

		this.getChildren().addAll(nom, money, save, fancy);
	}
}
