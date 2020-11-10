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
import io.github.martinheywang.products.api.model.Game;
import io.github.martinheywang.products.model.database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * The HomeView class is the controller class, in javafx terms, for the view
 * Home.fxml. It is a view when the user creates its first game.
 */
public class HomeView implements Initializable {

    @FXML
    TextField field;

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
