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
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
	@FXML
	private ProgressBar progression;

	// Variables ci-dessous à revoir, modifiers et déclaration.
	private static StringProperty reportProperty = new SimpleStringProperty();
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

		argentLabel.textProperty()
				.bind(Bindings.concat(argentProperty.asString(), " €"));
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
		// Xxx : progressBar doesn't update before the end of the loading

		Progression progress = new Progression();
		progression.progressProperty().bind(progress.progressProperty());

		// Sets the report dialog to a little text who says that the game is
		// still loading and sets the money label
		setReport("Bienvenue ! \nLe jeu est encore entrain de charger... "
				+ "Merci de patienter !\nCliquez sur cette bulle pour la "
				+ "fermer.", Color.LIGHTGRAY);
		argentProperty.set(partieToLoad.getArgent());

		partieEnCours = partieToLoad;
		// Sets the size of the table with the grid-size of the game
		appareils = new Appareil[partieToLoad.getTailleGrille()][partieToLoad
				.getTailleGrille()];

		int i = 0;
		for (Appareil appareil : partieToLoad.getAppareils()) {
			// Adds to the table the instance of the loaded Appareil
			appareils[appareil.getXy().getX()][appareil.getXy()
					.getY()] = appareil.toInstance(this);
			// Adds this device to the grid
			grille.add(appareils[appareil.getXy().getX()][appareil.getXy()
					.getY()], appareil.getXy().getX(), appareil.getXy().getY());

			progress.setProgress(i / partieToLoad.getAppareils().size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println(e.getLocalizedMessage());

			}
		}

		t = new Thread(new Play());
		t.start();

		argentLabel.setVisible(true);
		progression.setVisible(false);

	}

	/**
	 * This method closes the game and set up the stage on the home scene,
	 * where we can chose which game we want to load.
	 * 
	 * @see Main#initAccueil2()
	 */
	public void returnToHome() {
		appareils = null;
		t.interrupt();

		partieEnCours.save();
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

	class Progression {
		private final DoubleProperty progress = new SimpleDoubleProperty(0.0);

		public DoubleProperty progressProperty() {
			return progress;
		}

		public void setProgress(double progress) {
			this.progress.set(progress);
		}

		public double getProgress() {
			return progress.get();
		}
	}

	public void setArgent(long somme, boolean increase)
			throws NegativeArgentException {
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