package com.martin.view;

import com.martin.model.appareils.TypeAppareil;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/*
 * Martin
 * 21/01/2020
 * 
 * Contrôleur de Sol.fxml qui sert pour la construction des appareils.*/

public class SolContrôle {
	
	@FXML private Label coordonnées; //Label des coordonnées situé en haut de la page
	@FXML private VBox listeAppareils;	//Liste des appareils intégré dans le ScrollPane au milieu à droite
	
	private Dialog<Object> dialog;
	
	/*
	 * Martin
	 * 21/01/2020
	 * 
	 * Rôle : Mettre en forme la fenêtre, ajouter les layouts et les widgets.*/
	public void initialize() {
		for(int i = 0; i < TypeAppareil.values().length-1; i++) {
			//On ajoute les displayers des appareils dans la liste prévue à cet effet
			listeAppareils.getChildren().add(new Displayer(TypeAppareil.values()[i]));
			
			//On ajoute un listener du clic sur un displayer
			listeAppareils.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					try {
						dialog.setResult(((Displayer) event.getSource()).getTypeAppareil());
						dialog.close();
					} catch (Exception e) {
						System.out.println("ERREUR lors de la construction d'un appareil. Raison : "+e.getMessage());
					}
				}
				
			});
		}
		
	}
	
	/*
	 * Martin
	 * 21/01/2020
	 * 
	 * Rôle : met à jour les labels en fonction de données passées en paramètres.
	 * Args : x -> coordonnées x de l'appareil
	 * 		  y -> coordonnées y de l'appareil
	 *        dialog -> la boîte de dialogue à fermer*/
	public void setMainApp(int x, int y, Dialog<Object> dialog) {
		coordonnées.setText("X: "+x+" Y: "+y);
		this.dialog = dialog;
	}
}
