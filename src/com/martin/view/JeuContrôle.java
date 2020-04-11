package com.martin.view;

import java.sql.SQLException;
import java.util.List;

import com.martin.Main;
import com.martin.model.Coordonnees;
import com.martin.model.Partie;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.AppareilModel;
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
import javafx.concurrent.Task;
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
	private static final StringProperty reportProperty = new SimpleStringProperty();
	private static final LongProperty argentProperty = new SimpleLongProperty();

	private Thread t;
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
		// Save this instance (used a little bit later)
		final JeuContrôle controller = this;
		// The task defines how to load the game
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() {
				// Little message ("The game is still loading...")
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setReport(
								"Chargement de la partie en cours...\n"
										+ "L'opération peut durer quelques instants.",
								Color.INDIANRED);
					}
				});

				// Updating the field partieEnCours
				partieEnCours = partieToLoad;
				// Fetching the model of all devices in a list
				List<AppareilModel> devicesModel = partieToLoad
						.getAppareilsModel();

				final int taille = partieToLoad.getTailleGrille();
				// Creating the device if they aren't enough
				if (devicesModel.size() < Math.sqrt(taille)) {
					for (int x = 0; x < taille; x++) {
						for (int y = 0; y < taille; y++) {
							final Coordonnees coordonnees = Coordonnees
									.createOrQuery(new Coordonnees(x, y));

							devicesModel.add(
									new AppareilModel(coordonnees,
											partieToLoad));
						}
					}
				}

				// Variable i for progress
				int i = 1;
				// For all models
				for (AppareilModel model : devicesModel) {
					try {
						// Creating a new device using the model
						Appareil appareil = model.getType().getClasse()
								.getConstructor(AppareilModel.class,
										JeuContrôle.class)
								.newInstance(model, controller);

						// Adding it to the grid (view)
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								grille.add(appareil,
										appareil.getModel().getCoordonnees()
												.getX(),
										appareil.getModel().getCoordonnees()
												.getY());
							}
						});

					} catch (Exception e) {
						e.printStackTrace();
					}

					// Setting up the progress
					i++;
					progression.progressProperty()
							.set((double) i
									/ devicesModel.size());
				}

				// Starting the thread of game
				t = new Thread(new Play());
				t.start();

				// Last view modifications
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						argentLabel.setVisible(true);
						progression.setVisible(false);
						// Sets the report dialog to a little text who says that
						// the game is
						// still loading and sets the money label
						setReport("Bienvenue !", Color.CORNFLOWERBLUE);
						argentProperty.set(partieToLoad.getArgent());
					}
				});
				// Here we must return something of type Void (this type can't
				// be instantiated), so we return null
				return null;
			}
		};

		// Launching obviously the task defined in a new thread
		Thread loading = new Thread(task);
		loading.start();

	}

	/**
	 * This method closes the game and set up the stage on the home scene,
	 * where we can chose which game we want to load.
	 * 
	 * @see Main#initAccueil2()
	 */
	@FXML
	public void returnToHome() throws SQLException {
		t.interrupt();

		partieEnCours.save(null);
		// Todo : save the game
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
							partieEnCours
									.getAppareil(Appareil_Acheteur.liste.get(i))
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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (increase) {
					argentProperty.set(argentProperty.get() + somme);
				} else {
					if (argentProperty.get() < somme)
						setReport(
								"Impossibilité de modifier la somme d'argent, solde trop faible.",
								Color.DARKRED);
					else {
						argentProperty.set(argentProperty.get() - somme);
					}
				}
			}
		});
		partieEnCours.setArgent(somme, increase);
	}

	public void setAppareil(Appareil appareil, boolean ignoreCost) {

		// Todo : method JeuContrôle.setAppareil(Appareil, boolean)

	}

	/**
	 * Shows the report if hided and sets the text to String in parameter.
	 * The must also indicates a JavaFX Color which will be the color of
	 * the border.
	 * 
	 * @param text        the text to show
	 * @param colorBorder the new color of the border
	 */
	public void setReport(String text, Color colorBorder) {
		reportProperty.set(text);
		report.getStyleClass().add("report");
		report.setStyle("-fx-border-color: " + String.format("#%02X%02X%02X",
				(int) (colorBorder.getRed() * 255),
				(int) (colorBorder.getGreen() * 255),
				(int) (colorBorder.getBlue() * 255)) + ";");
		report.setVisible(true);
	}

	public Partie getPartieEnCours() {
		return partieEnCours;
	}
}