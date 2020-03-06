package com.martin.view;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.Partie;
import com.martin.Stats;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Acheteur;
import com.martin.model.exceptions.NegativeArgentException;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JeuContrôle {
	
	//L'instance de main, pour charger les différentes pages
	public static Main main;
	
	@FXML GridPane grille;
	@FXML private Label argentLabel;
	@FXML private Label report;
	@FXML private Button upgradeGrid, recherche;
	
	//Le stage de recherche
	Stage research = new Stage();
	
	//Variables ci-dessous à revoir, modifiers et déclaration.
	private StringProperty reportProperty = new SimpleStringProperty();
	private Thread t;
	
	private static Partie partieEnCours;
	
	
	public void initialize() {}
	public void setMainApp(Main main, Partie partieToLoad) throws Exception {
		// Todo : setMainApp method
		
		JeuContrôle.main = main;
		JeuContrôle.partieEnCours = partieToLoad;
		
		for(int x = 0; x < partieToLoad.getTailleGrille(); x++) {
			for(int y = 0; y < partieToLoad.getTailleGrille(); y++) {
				final Appareil appareil = Connect_SQLite.getAppareil(Connect_SQLite.getCoordonnéesDao().queryBuilder().where()
						.eq("x", x).and()
						.eq("y", y).queryForFirst(), partieToLoad);
				
				grille.add(appareil, appareil.getXy().getX(), appareil.getXy().getY());
			}
		}
		
		grille.setFocusTraversable(true);
		argentLabel.setText(String.valueOf(partieToLoad.getArgent())+" €");
		
		t = new Thread(new Play());
		t.start();
		
		report.textProperty().bind(reportProperty);
		report.setVisible(false);
		report.setTooltip(new Tooltip("Cliquez pour cacher..."));
		
		partieToLoad.save();
		
	}
	/*La définition du Thread à refaire : 
	 * J'ai pensé faire un système de liste de chose à faire via une liste publique et statique
	 * qui va demander l'action. Comme ça, il n'y a que les actions nécessaires qui sont réalisées.
	 * Résultat: les sols ne font pas lagger pour rien le jeu.*/
	class Play implements Runnable{

		@Override
		public void run() {
			try {
				while(true){
					Thread.sleep(750);
					for(int i = 0; i < Appareil_Acheteur.liste.size(); i++){
						try {
							Ressource[] res = {Ressource.NONE};
							getGrilleAppareils(Appareil_Acheteur.liste.get(i)).action(res);
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
	//On a demandé la fenêtre de recherche
	@FXML private void research() {
		
	}
	//Visuel du bouton AMÉLIORER LA GRILLE
	@FXML private void upgradeGrid() {
		/*Si on a pas assez d'argent, plutôt que d'ajouter un message dans le report,
		on ouvre une boîte de dialogue.*/
	}
	@FXML private void upgradeGridEntered() {
		upgradeGrid.setText("-"+Stats.prixAgrandissement+" €");
	}
	@FXML private void upgradeGridExited() {
		upgradeGrid.setText("AGRANDIR LA GRILLE");
	}
	/**
	 * <h1>setReport</h1>
	 * <p>Add a dialog in the bottom right corner of the grid</p>
	 * 
	 * @param text the text to display
	 * @param border the color of the border of the dialog
	 */
	public  void setReport(String text, Color border) {
		reportProperty.set(text);
		final String hex = String.format("#%02X%02X%02X", ((int)(border.getRed()*255)), 
				(int)(border.getGreen()), (int) (border.getBlue()));
		report.setStyle("-fx-border-color: "+hex+";");
		report.getStyleClass().add("report");
		
		
		report.setVisible(true);
	}
	
	/**
	 * @return the widget grid
	 * 
	*/
	public GridPane getGridPane() {
		return grille;
	}
	/**
	 * @param xy les coordonnées
	 * @return un Appareil au coordonnées données
	 */
	public Appareil getGrilleAppareils(Coordonnées xy) throws NullPointerException{
		try {
			return Connect_SQLite.getAppareilDao().queryBuilder().where()
					.eq("partie_idPartie", partieEnCours.getID()).and()
					.eq("xy_idCoordonnées", xy.getID())
					.queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @return the argent property
	 */
	public long getArgent() {
		return partieEnCours.getArgent();
	}
	/**
	 * @return la taille de la grille
	 */
	public int getTailleGrille() {
		return partieEnCours.getTailleGrille();
	}
	/**
	 * @return partieEnCours the current game
	 */
	public Partie getPartieEnCours() {
		return partieEnCours;
	}
	public static JeuContrôle get() {
		try {
			JeuContrôle controller = new JeuContrôle();
			controller.initialize();
			controller.setMainApp(main, partieEnCours);
			return controller;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * <h2>setAppareil</h2>
	 * <br/>Modifie une case d'appareil.
	 * 
	 * @param appareil l'appareil qui remplace
	 * @param idOldAppareil id of the old device
	 * @param ignoreCost if the cost will be ignrored
	 * 
	*/
	public void setAppareil(Appareil appareil, int idOldAppareil, boolean ignoreCost) {
		try {
			if(!ignoreCost)
				setArgent(((int) appareil.getType().getClasse().getMethod("getPrix").invoke(null))
						, false);
			partieEnCours.setAppareil(appareil, idOldAppareil, ignoreCost);
			grille.add(appareil, appareil.getXy().getY(), appareil.getXy().getX());
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @author Martin
	 * 
	 *         <b>setArgent</b>
	 *         <p>
	 *         Modifie le solde d'argent en jeu
	 *         </p>
	 * 
	 * @param somme la somme d'argent à enlever ou supprimer
	 * @param increase vaut true pour ajouter, vaut false pour enlever de l'argent
	 * 
	 * @throws NegativeArgentException Si la somme d'argent devient négative
	 * 
	 */
	public void setArgent(long somme, boolean increase){
		//Modifie le solde d'argent de la partie en cours
		final boolean edit = partieEnCours.setArgent(somme, increase);
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				if(edit){
					//Modifie le libéllé d'argent
					argentLabel.setText(String.valueOf(partieEnCours.getArgent()+" €"));
				}
			}
		});
	}
}