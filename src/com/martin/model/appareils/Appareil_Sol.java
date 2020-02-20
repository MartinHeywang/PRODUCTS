package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.Main;
import com.martin.Partie;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement_Aucun;
import com.martin.model.appareils.orientation.Entrées_Aucune;
import com.martin.model.appareils.orientation.Sorties_Aucune;
import com.martin.view.JeuContrôle;
import com.martin.view.SolContrôle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

public class Appareil_Sol extends Appareil {
	
	private static SimpleIntegerProperty prix;
	
	public Appareil_Sol(Coordonnées xy, Direction direction, NiveauAppareil niveau, JeuContrôle controller, Partie partie) 
			throws FileNotFoundException {
		super(xy, TypeAppareil.SOL, direction, niveau, controller, partie);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/Sol.fxml"));
							
					Dialog<Object> dialog;
					DialogPane dialogPane;
							
					dialogPane = (DialogPane) loader.load();
					dialog = new Dialog<Object>();
					dialog.setTitle("Sélection d'appareil - PRODUCTS.");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.stage);
					dialog.initModality(Modality.NONE);
							
					SolContrôle SController = loader.getController();
					SController.setMainApp(new Coordonnées(xy.getX(), xy.getY()), dialog);
							
					dialog.showAndWait();
					if(!dialog.getResult().getClass().equals(ButtonType.class)){
						controller.setAppareil(new Coordonnées(xy.getX(), xy.getY()), 
								((TypeAppareil) dialog.getResult()).getClasse().getConstructor(
								Coordonnées.class, Direction.class, NiveauAppareil.class,  
								JeuContrôle.class).newInstance(
										new Coordonnées(xy.getX(), xy.getY()), 
										Direction.UP, NiveauAppareil.NIVEAU_1, controller));
					}
				}catch (Exception e) {
					controller.setReport("ERREUR dans Appareil_Sol.Appareil_Sol(...).new EventHandler() {...} dans la méthode "
									+ "handle. Raison :\n" + e.getLocalizedMessage(), Color.DARKRED);
					e.printStackTrace();
				}
			}
		});
		
		entrées = new Entrées_Aucune();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Aucune();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Aucun();
	}
	
	
	
	public void checkRotation(String rotate) {
	}
	@Override
	public void action(Ressource[] resATraiter) {}
	@Override
	public void destruction() {}
	
	public static void initializeData() {
		prix = new SimpleIntegerProperty(0);
	}
	public static int getPrix() {
		return prix.get();
	}
	
}
