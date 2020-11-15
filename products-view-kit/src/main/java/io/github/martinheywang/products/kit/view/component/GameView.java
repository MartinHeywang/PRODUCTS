/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.kit.view.component;

import java.time.format.DateTimeFormatter;

import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A GameView is a Java FX {@link javafx.scene.Node} that displays a particular {@link io.github.martinheywang.products.api.model.Game}.
 */
public final class GameView extends VBox {

	/**
	 * Creates a new GameView.
	 * 
	 * @param data the displayed game
	 */
	public GameView(Game data) {
		this.setPadding(new Insets(5));

		this.setDisplayed(data);
	}

	/**
	 * Sets the displayed game
	 * 
	 * @param game the new displayed game
	 */
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
