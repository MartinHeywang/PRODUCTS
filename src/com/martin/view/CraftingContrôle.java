package com.martin.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.martin.Stats;
import com.martin.model.LocatedImage;
import com.martin.model.Paquet;
import com.martin.model.Recette3;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil_Trieur;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CraftingContrôle {

	@FXML VBox list;
	@FXML Label label, value;
	
	public static int x = 0, y = 0;
	int z = 0;
	
	public CraftingContrôle() {}
	
	public void initialize() {}
	public void setModeOnCrafting() {
		
		for(int i = 0; i < Stats.schéma; i++) {
			if((i+25) < Ressource.values().length)
				list.getChildren().add(new Recette3(new Paquet(Ressource.values()[i+25], 1)));
		}
		
		for(int i = 0; i < list.getChildren().size(); i++) {
			list.getChildrenUnmodifiable().get(i).setOnMouseEntered(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					((Node) arg0.getSource()).setStyle("-fx-background-color: lightgray;");
					value.setText("Valeur : "+String.valueOf((
							(Recette3) arg0.getSource()).getPaquet().getRessource().getValue()));
				}
						
			});
			list.getChildrenUnmodifiable().get(i).setOnMouseExited(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					((Node) arg0.getSource()).setStyle("-fx-background-color: white;");
					value.setText("");
				}
						
			});
		}
	}
	
	public void setModeOnTrieur(boolean bool) {
		label.setText("Sélectionnez une ressource");
		z = 0;
		
		value.setMaxHeight(0.0);
		
		for(int i = 0; i<Ressource.values().length/3; i++) {
			HBox hbox = new HBox();
			for(int j = 0; j<3; j++) {
				try {
					ImageView img = new ImageView(new LocatedImage(new FileInputStream(new File(
							Ressource.values()[z].getURL())), Ressource.values()[z].getURL()));
					AnchorPane root = new AnchorPane(img);
					
					root.setOnMouseEntered(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							value.setText("Valeur : "+String.valueOf((
									(Recette3) arg0.getSource()).getPaquet().getRessource().getValue()));
							System.out.println(String.valueOf((
									(Recette3) arg0.getSource()).getPaquet().getRessource().getValue()));
						}
						
					});
					
					root.setOnMouseExited(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							value.setText("");
						}
						
					});
					
					root.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							if(bool) {
								((Appareil_Trieur) JeuContrôle.images[x][y].getAppareil()).setCritère1(
										Ressource.getForURL(
												((LocatedImage) img.getImage()).getURL()));
								AppareilsContrôle.setImgCrit1(((LocatedImage) img.getImage()).getURL());
							}
							else {
								((Appareil_Trieur) JeuContrôle.images[x][y].getAppareil()).setCritère2(
										Ressource.getForURL(
												((LocatedImage) img.getImage()).getURL()));
								AppareilsContrôle.setImgCrit2(((LocatedImage) img.getImage()).getURL());
							}
								
							AppareilsContrôle.stage.close();
						}
						
					});
					root.setMinWidth(166);
					
					AnchorPane.setBottomAnchor(img, 5d);
					AnchorPane.setRightAnchor(img, 33d);
					AnchorPane.setTopAnchor(img, 5d);
					AnchorPane.setLeftAnchor(img, 33d);
					
					hbox.getChildren().add(root);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				z++;
			}
			list.getChildren().add(hbox);
			
		}
	}

}