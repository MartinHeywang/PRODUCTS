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
