package com.martin.view;

import java.util.List;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.Partie;
import com.martin.Stats;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Acheteur;
import com.martin.model.appareils.Direction;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.appareils.TypeAppareil;
import com.martin.model.exceptions.NegativeArgentException;

import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
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
	Main main;
	
	//La grille principale de jeu
	@FXML GridPane grille;
	@FXML private Label argentLabel;
	@FXML private Label report;
	@FXML private Button upgradeGrid, recherche;
	
	//Le stage de recherche
	Stage research = new Stage();
	
	//---Variables ci-dessous à revoir, modifiers et déclaration.
	private Appareil[][] appareil = new Appareil[20][20];
	private StringProperty reportProperty = new SimpleStringProperty();
	private Thread t;
	
	private Partie partieEnCours;
	
	
	public void initialize() {
		for(int i = 0; i < TypeAppareil.values().length; i++) {
			try {
				TypeAppareil.values()[i].getClasse().getMethod("initializeData").invoke(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public void setMainApp(Main main, Partie partieToLoad) {
		this.main = main;
		this.partieEnCours = partieToLoad;
		
		argentLabel.setText(String.valueOf(partieToLoad.getArgent()));
		
		List<Appareil> listeAppareils = 
				Connect_SQLite.getAppareilDao().queryBuilder().where().eq("partie", partieToLoad).query();
		
		for(Appareil appareil : listeAppareils) {
			final int x = appareil.getXy().getX();
			final int y = appareil.getXy().getY();
			this.appareil[x][y] = appareil.getType().getClasse().getConstructor(Coordonnées.class,
					Direction.class, NiveauAppareil.class, JeuContrôle.class, Partie.class)
					.newInstance(new Coordonnées(x, y), appareil.getDirection(), appareil.getNiveau(), this);
			this.grille.add(this.appareil[x][y], x, y);
		}
		
		grille.setFocusTraversable(true);
		argentLabel.setText(String.valueOf(argent.get())+" €");
		
		t = new Thread(new Play());
		t.start();
		
		report.textProperty().bind(reportProperty);
		report.setVisible(false);
		report.setTooltip(new Tooltip("Cliquez pour fermer..."));
		
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
				System.out.println(
						"ERREUR dans JeuContrôle.Play dans la méthode run. Raison :\n" + e.getLocalizedMessage());
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
	public Appareil getGrilleAppareils(Coordonnées xy){
		return appareil[xy.getX()][xy.getY()];
	}
	/**
	 * @return the argent property
	 */
	public SimpleLongProperty getArgentProperty() {
		return argent;
	}
	/**
	 * @return la taille de la grille
	 */
	public int getTailleGrille() {
		return tailleGrille;
	}
	
	/**
	 * @author Martin
	 * 28 janv. 2020 | 15:14:44
	 * 
	 * <b>setAppareil</b>
	 * <p>Modifie une case d'appareil.</p>
	 * 
	 * Args :
	 * @param xy coordonnées du nouvel appareil
	 * @param appareil l'appareil qui remplace
	 * 
	*/
	public void setAppareil(Coordonnées xy, Appareil appareil) {
		try {
			setArgent(((int) appareil.getType().getClasse().getMethod("getPrix").invoke(null))
					, false);
			this.appareil[xy.getX()][xy.getY()] = appareil;
			grille.add(this.appareil[xy.getX()][xy.getY()], xy.getX(), xy.getY());
		} catch (NegativeArgentException e) {
			setReport(e.getMessage(), Color.DARKRED);
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
	 * @param somme    la somme d'argent à enlever ou supprimer
	 * @param increase vaut true pour ajouter, vaut false pour enlever de l'argent
	 * 
	 * @throws NegativeArgentException Si la somme d'argent devient négative
	 * 
	 */
	public void setArgent(long somme, boolean increase) throws NegativeArgentException{
		if(increase)
			argent.set(argent.get()+somme);
		else {
			if(argent.get() >= somme)
				argent.set(argent.get()-somme);
			else {
				throw new NegativeArgentException();
			}
		}
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				argentLabel.setText(String.valueOf(argent.get())+" €");
			}
		});
	}
}