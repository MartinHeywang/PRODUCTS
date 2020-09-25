package com.martinheywang.products.model;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martinheywang.products.model.database.Database;
import com.martinheywang.products.model.devices.DeviceModel;
import com.martinheywang.products.toolbox.MoneyFormat;
import com.martinheywang.products.view.Displayable;
import com.martinheywang.products.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * <p>
 * The Game class represents a Game in PRODUCTS. In contains all the data about
 * them. This object may be stored in the database.
 * </p>
 * <p>
 * This class is using JavaFX Properties, you can at any time observe one of
 * them and apply changes in consequence.
 * </p>
 * <p>
 * It's near of a Java Bean:
 * <ul>
 * <li>It has a default constructor</li>
 * <li>It has a getter and setter for <em>almost</em> all properties and
 * values</li>
 * </ul>
 * </p>
 * <p>
 * It implements Displayable, that means that you can get at any time a display
 * (as a {@link javafx.scene.Node}) of it.
 * </p>
 * 
 * @author Martin Heywang
 */
@DatabaseTable(tableName = "game")
public class Game implements Displayable<Game> {

    /**
     * The ID of this game in the database. May be null.
     */
    @DatabaseField(columnName = "id", generatedId = true)
    private Long id;

    /**
     * The accesible name (shown in the UI) of this game
     */
    @DatabaseField
    private String name;

    /**
     * As a Game can be saved in the database, we register the time of the last
     * saving session.
     */
    @DatabaseField
    private String lastSave;

    /**
     * The money amount that "owns" this Game.
     */
    @DatabaseField
    private BigInteger money;

    /**
     * The grid size of this game.
     */
    @DatabaseField
    private Integer gridSize;

    /**
     * The grow of this game.
     */
    @DatabaseField
    private BigInteger grow;

    public Game() {
    }

    /**
     * Creates a new <i>game</i>. Saves it directly in the database.
     * 
     * @param nom the name of the new game
     * @throws SQLException if this object can't be registered in the database
     */
    public Game(String name) throws SQLException {
        this.name = name;
        this.gridSize = 3;
        this.money = new BigInteger("1250");
        this.grow = new BigInteger("0");
    }

    /**
     * Saves the game and all its models.
     * 
     * @return true, if the saving process succedeed, else false
     */
    public boolean save() {
        try {
            lastSave = LocalDateTime.now().toString();
            Database.createDao(Game.class).createOrUpdate(this);
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Refreshes the list of devices model and return it then.
     * 
     * @return a list of all devices of this game
     * @throws SQLException if the devices couldn't be loaded
     */
    public List<DeviceModel> loadDevicesModel() throws SQLException {
        final List<DeviceModel> devicesModel = Database.createDao(DeviceModel.class).queryBuilder().where()
                .eq("game_id", this.id).query();

        return devicesModel;
    }

    @Override
    public Displayer<Game> getDisplayer() {
        final VBox root = new VBox();

        final Label nom = new Label();
        nom.setUnderline(true);
        nom.setAlignment(Pos.TOP_CENTER);
        nom.setText(this.getName());
        nom.setWrapText(true);
        

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        final Label money = new Label();
        money.setText("Argent en compte: " + MoneyFormat.getSingleton().format(this.getMoney()));

        final Label save = new Label();
        save.setText("Dernière sauvegarde: " + this.getLastSave().format(formatter));

        root.getChildren().addAll(nom, money, save);

        root.setPadding(new Insets(5));
        return new Displayer<Game>(root, this);
    }

    // PROPERTIES GETTER

    /**
     * May be null.
     * 
     * @return the id of this object in the database.
     */
    public Long getID() {
        return this.id;
    }

    /**
     * Returns the accesible name of this Game object.
     * 
     * @return the name of this game
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the size of the grid.
     * 
     * @return the grid-size
     */
    public Integer getGridSize() {
        return this.gridSize;
    }

    /**
     * Returns how money this game has.
     * 
     * @return the money amount
     */
    public BigInteger getMoney() {
        return this.money;
    }

    // PROPERTIES GETTER

    /**
     * Returns how many this game generate each iterations of the game loop.
     * 
     * @return the grow
     */
    public BigInteger getGrow() {
        return this.grow;
    }

    /**
     * The timing of the last save of this Game object.
     * 
     * @return the date & time of the last save of this object (as LocalDateTime,
     *         also known as JodaTime)
     */
    public LocalDateTime getLastSave() {
        return LocalDateTime.parse(this.lastSave);
    }

    /**
     * Sets the new accesible name of this Game object.
     * 
     * @param newName the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the new amount of money of this Game object
     * 
     * @param money the new money-amount
     */
    public void setMoney(BigInteger money) {
        this.money = money;
    }

    /**
     * Sets the grow property
     * 
     * @param grow the new grow value
     */
    public void setGrow(BigInteger grow) {
        this.grow = grow;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + money + "€ }";
    }
}
