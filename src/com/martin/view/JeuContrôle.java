package com.martin.view;

import java.sql.SQLException;

import com.martin.Main;
import com.martin.Partie;
import com.martin.model.Coordonnées;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Acheteur;
import com.martin.model.exceptions.NegativeArgentException;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class JeuContrôle {

	// L'instance de main, pour charger les différentes pages
	private Main main;

	@FXML
	private GridPane grille;
	@FXML
	private Label argentLabel;
	@FXML
	private Label report;
	@FXML
	private Button upgradeGrid, recherche;

	// Variables ci-dessous à revoir, modifiers et déclaration.
	private static StringProperty reportProperty = new SimpleStringProperty();
	private static StringProperty argentLabelProperty = new SimpleStringProperty();
	private static LongProperty argentProperty = new SimpleLongProperty();

	private Thread t;

	private Appareil[][] appareils;

	private Partie partieEnCours;

	public void initialize() {
		report.textProperty().bind(reportProperty);
		report.setVisible(false);
		report.setTooltip(new Tooltip("Cliquez pour cacher..."));
		report.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				report.setVisible(false);
			}
		});

		argentLabel.textProperty().bind(argentLabelProperty);
		argentLabelProperty
				.bind(new SimpleStringProperty(argentProperty.get() + " €"));

		grille.setFocusTraversable(true);
	}

	/**
	 * This method sets which instance of main should be used.
	 * 
	 * @param main the instance of main
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}

	/**
	 * Loads a game with all its informations and lauch the thread.
	 * 
	 * @param partieToLoad the game to load
	 */
	public void load(Partie partieToLoad) throws SQLException {
		this.partieEnCours = partieToLoad;
		// Set the size of the table with the grid-size of the game
		appareils = new Appareil[partieToLoad.getTailleGrille()][partieToLoad
				.getTailleGrille()];
		// For the list of devices in the partieToLoad
		for (Appareil appareil : partieToLoad.getAppareils()) {
			this.appareils[appareil.getXy().getX()][appareil.getXy()
					.getY()] = appareil.toInstance(this);
			grille.add(this.appareils[appareil.getXy().getX()][appareil.getXy()
					.getY()], appareil.getXy().getX(), appareil.getXy().getY());
		}

		t = new Thread(new Play());
		t.start();

		reportProperty
				.set("Bienvenue !\nCliquez sur cette bulle pour la fermer.");
		argentProperty.set(partieToLoad.getArgent());
		report.setVisible(true);

		try {
			setArgent(45820, true);
		} catch (NegativeArgentException e) {
			// Catch bloc automatiquement généré
			System.out.println(e.getLocalizedMessage());

		}
		setReport("Just a simple test", Color.DARKORANGE);
	}

	/**
	 * This method closes the game and set up the stage on the home scene,
	 * where we can chose which game we want to load.
	 * 
	 * @see Main#initAccueil2()
	 */
	public void returnToHome() {
		main.initAccueil2();
	}

	class Play implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(750);
					for (int i = 0; i < Appareil_Acheteur.liste.size(); i++) {
						try {
							getGrilleAppareils(Appareil_Acheteur.liste.get(i))
									.action(new Stock());
							argentLabel.setTextFill(Color.WHITE);
						} catch (NegativeArgentException e) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									argentLabel.setTextFill(Color.DARKRED);
								}
							});

						}
					}
				}
			} catch (IllegalArgumentException | InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void setArgent(long somme, boolean increase)
			throws NegativeArgentException {
		// Todo : setArgent doesn't work properly
		if (increase) {
			argentProperty.set(argentProperty.get() + somme);
		} else {
			if (argentProperty.get() < somme)
				throw new NegativeArgentException();
			else {
				argentProperty.set(argentProperty.get() - somme);
			}
		}
	}

	public void setReport(String text, Color colorBorder) {
		reportProperty.set(text);
		report.getStyleClass().add("report");
		report.setStyle("-fx-border-color: " + String.format("#%02X%02X%02X",
				(int) (colorBorder.getRed() * 255),
				(int) (colorBorder.getGreen() * 255),
				(int) (colorBorder.getBlue() * 255)) + ";");
		report.setVisible(true);
	}

	public Appareil getGrilleAppareils(Coordonnées xy)
			throws NullPointerException {
		return null;
	}

	public Partie getPartieEnCours() {
		return partieEnCours;
	}
}