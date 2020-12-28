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
import java.util.Arrays;

import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.api.utils.MoneyFormat;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A GameView is a Java FX {@link javafx.scene.Node} that displays a particular
 * {@link io.github.martinheywang.products.api.model.Game}.
 */
public final class GameView extends HBox {

	private Game game;

	private boolean isCollapsed = true;

	private VBox text;
	private Label help;
	private Label money;
	private Label save;

	/**
	 * Creates a new GameView.
	 * 
	 * @param data the displayed game
	 */
	public GameView(Game data) {
		this.setPadding(new Insets(10d));
		this.setBackground(ViewUtils.bgDefault);
		this.setAlignment(Pos.CENTER);
		this.setDisplayed(data);
	}

	/**
	 * Sets the displayed game
	 * 
	 * @param game the new displayed game
	 */
	public void setDisplayed(Game game) {
		this.getChildren().clear();
		this.game = game;

		final Label name = new Label(game.getName());
		name.getStyleClass().add("h5");
		name.setMaxWidth(250d);

		this.help = new Label("Cliquez pour afficher les détails.");
		help.getStyleClass().add("precision");

		this.money = new Label("Argent : " + MoneyFormat.getSingleton().format(this.game.getMoney()));
		this.save = new Label("Dernière sauvegarde : "
				+ this.game.getLastSave().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")));

		this.text = new VBox();
		text.getChildren().addAll(name, help);
		text.setSpacing(6d);

		// This pane is used to create a void between components.
		final Pane grow = new Pane();
		HBox.setHgrow(grow, Priority.ALWAYS);
		this.getChildren().addAll(text, grow);

		this.setOnMouseEntered(event -> {
			this.setBackground(ViewUtils.bgHover);
			help.getStyleClass().add("precision-light");
			name.getStyleClass().add("full-white");
		});
		this.setOnMouseExited(event -> {
			this.setBackground(ViewUtils.bgDefault);
			help.getStyleClass().add("precision-light");
			name.getStyleClass().remove("full-white");
		});
		this.setOnMouseClicked(event -> {
			if (isCollapsed) {
				showAll();
			} else {
				collapse();
			}
			this.isCollapsed = !this.isCollapsed;
		});
	}

	private void showAll() {
		help.setText("Cliquez pour cacher les détails.");
		this.text.getChildren().addAll(1, Arrays.asList(money, save));
	}

	private void collapse() {
		this.text.getChildren().removeAll(money, save);
		this.help.setText("Cliquez pour afficher les détails.");
	}

	/**
	 * Adds a button on the right of the view.
	 * 
	 * @param name    the text of the button
	 * @param handler what will be executed if the button is fired.
	 */
	public void allowAction(String name, EventHandler<ActionEvent> handler) {
		final Button button = new Button(name);
		button.setOnAction(handler);
		button.getStyleClass().add("fancy-border");
		button.setCursor(Cursor.HAND);
		this.getChildren().add(button);
	}
}
