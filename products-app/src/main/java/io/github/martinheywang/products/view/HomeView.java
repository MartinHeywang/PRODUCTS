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
package io.github.martinheywang.products.view;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.martinheywang.products.Main;
import io.github.martinheywang.products.api.database.Database;
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.kit.view.component.SVGImage;
import io.github.martinheywang.products.kit.view.utils.ViewUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The HomeView class is the controller class, in javafx terms, for the view
 * Home.fxml. It is a view when the user creates its first game.
 */
public final class HomeView implements Initializable {

    @FXML
    private Parent root;
    @FXML
    private HBox logoContainer, stageBar;

    @FXML
    private TextField field;
    @FXML
    private Button reduceButton, closeButton;

    Main main;

    /**
     * Creates a game and launch it.
     */
    @FXML
    public void create() {
        if (!this.field.getText().isEmpty())
            try {
                final Game game = new Game(this.field.getText());
                Database.createDao(Game.class).create(game);
                this.main.initGame(game);
            } catch (final Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        this.root.getStylesheets().addAll(ViewUtils.class.getResource("/css/General.css").toExternalForm());

        closeButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Close.svg"), 20, 20));
        reduceButton.setGraphic(new SVGImage(getClass().getResource("/images/icons/Reduce.svg"), 20, 20));
        
        closeButton.setOnMouseClicked(event -> {
            Platform.exit();
        });
        reduceButton.setOnMouseClicked(event -> {
            ((Stage) reduceButton.getScene().getWindow()).setIconified(true);
        });

        stageBar.setOnMousePressed(pressEvent -> {
            stageBar.setOnMouseDragged(dragEvent -> {
                final Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        this.logoContainer.getChildren().add(0, new SVGImage(getClass().getResource("/images/icons/logo.svg"), 50, 75));
    }

    /**
     * Sets the object main and initialize the data in the widgets.
     * 
     * @param main the object to set
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

}
