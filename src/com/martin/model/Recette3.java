package com.martin.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.martin.model.appareils.Appareil_Assembleur;
import com.martin.view.AppareilsContrôle;
import com.martin.view.CraftingContrôle;
import com.martin.view.JeuContrôle;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Recette3 extends BorderPane {

	Recette3 recette3;
	Paquet pqt;
	
	
	ImageView image1 = new ImageView(), image2 = new ImageView(), image3 = new ImageView();
	Label name = new Label(), labelQte1 = new Label(), labelQte2 = new Label(), labelQte3 = new Label();
	
	BorderPane composant1 = new BorderPane(), composant2 = new BorderPane(), composant3 = new BorderPane();
	BorderPane box = new BorderPane();
	
	
	private final int prefWidthLabel = 160;
	private final Pos posLabel = Pos.CENTER;
	
	
	
	public Recette3(Paquet pqt) {
		this.pqt = pqt;
		
		this.recette3 = this;
		
		this.name.setPrefWidth(500);
		this.name.setAlignment(posLabel);
		this.name.setStyle("-fx-font-weight : bold");
		
		this.labelQte1.setPrefWidth(prefWidthLabel);
		this.labelQte1.setAlignment(posLabel);
		this.labelQte1.setStyle("-fx-font-weight : bold");
		
		this.labelQte2.setPrefWidth(prefWidthLabel);
		this.labelQte2.setAlignment(posLabel);
		this.labelQte2.setStyle("-fx-font-weight : bold");
		
		this.labelQte3.setPrefWidth(prefWidthLabel);
		this.labelQte3.setAlignment(posLabel);
		this.labelQte3.setStyle("-fx-font-weight : bold");
		
		this.name.setText(pqt.getRessource().getNom());
		this.setTop(this.name);
		
		try {
			image1
			.setImage(
					new Image(
							new FileInputStream(
									new File(
											pqt.getRessource()
											.getRecette().get(0)
											.getRessource()
											.getURL()))));
			
			image2.setImage(new Image(new FileInputStream(new File(pqt.getRessource().getRecette().get(1).getRessource().getURL()))));
			image3.setImage(new Image(new FileInputStream(new File(pqt.getRessource().getURL()))));
			
			labelQte1.setText(String.valueOf(pqt.getRessource().getRecette().get(0).getQuantité()) +" "+ pqt.getRessource().getRecette().get(0).getRessource().getNom().toLowerCase());
			labelQte1.setStyle("-fx-font-color: gray;");
			
			labelQte2.setText(String.valueOf(pqt.getRessource().getRecette().get(1).getQuantité()) +" "+ pqt.getRessource().getRecette().get(1).getRessource().getNom().toLowerCase());
			labelQte2.setStyle("-fx-font-color: gray;");
			
			labelQte3.setText(String.valueOf(pqt.getQuantité()) +" "+ pqt.getRessource().getNom().toLowerCase());
			labelQte3.setStyle("-fx-font-color: darkgray;");
			
			this.composant1.setCenter(image1);
			this.composant1.setBottom(labelQte1);
			
			this.composant2.setCenter(image2);
			this.composant2.setBottom(labelQte2);
			
			this.composant3.setCenter(image3);
			this.composant3.setBottom(labelQte3);
			this.composant3.setStyle("-fx-background-color: lightgray;");
			
			box.setLeft(this.composant1);
			box.setCenter(this.composant2);
			box.setRight(this.composant3);
			
			this.setMinHeight(150);
			this.setCenter(box);
			
			VBox.setMargin(this, new Insets(5, 0, 5, 0));
			
			
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					AppareilsContrôle.stage.close();
					((Appareil_Assembleur) JeuContrôle.images[CraftingContrôle.x][CraftingContrôle.y].getAppareil()).setProduit(pqt.getRessource());
				}
				
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Paquet getPaquet() {
		return pqt;
	}

}
