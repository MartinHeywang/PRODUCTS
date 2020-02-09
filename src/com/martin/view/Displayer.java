package com.martin.view;

import java.io.File;
import java.io.FileInputStream;

import com.martin.model.Ressource;
import com.martin.model.appareils.TypeAppareil;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Displayer extends BorderPane {
	
	
	private Label nom;
	private ImageView image;
	private Label infos;
	
	
	private Ressource ressource;
	private TypeAppareil typeAppareil;
	
	/**
	 * <h1>Displayer</h1>
	 * <p>Create an object who displays the object in parameter.</p>
	 * @param ressource the object to display
	 */
	public Displayer(Ressource ressource) {
		
		try {
			this.ressource = ressource;
			
			nom = new Label();
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(ressource.getNom());
			this.setTop(nom);
			
			
			image = new ImageView();
			image.setImage(new Image(new FileInputStream(new File(ressource.getURL()))));
			this.setCenter(image);
			
			
			infos = new Label();
			infos.setAlignment(Pos.TOP_CENTER);
			infos.setText("Prix de vente : "+String.valueOf(ressource.getValue()) +" €");
			this.setBottom(infos);
			
			
		} catch(Exception e) {
			System.out.println("ERREUR lors de la création d'un Displayer à partir d'une Ressource. "
					+ "Raison :\n"+e.getMessage());
		}
	}
	
	/**
	 * <h1>Displayer</h1>
	 * <p>Creat an object who displays the object in parameter</p>
	 * @param appareil the object to display
	 */
	public Displayer(TypeAppareil appareil){
		try {
			this.typeAppareil = appareil;
			
			nom = new Label();
			nom.setUnderline(true);
			nom.setAlignment(Pos.TOP_CENTER);
			nom.setText(appareil.getNom());
			nom.setWrapText(true);
			this.setTop(nom);
			
			
			image = new ImageView();
			image.setImage(new Image(new FileInputStream(new File("images/machines niveau 1/"+appareil.getURL()))));
			this.setRight(image);
			
			
			infos = new Label();
			infos.setAlignment(Pos.TOP_CENTER);
			infos.setText("\nPrix : "+String.valueOf( appareil.getClasse()
					.getMethod("getPrix").invoke(null))+" €\n\n"+appareil.getDescription());
			infos.setWrapText(true);
			this.setLeft(infos);
			
			this.setPadding(new Insets(3, 3, 3, 3));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				final String style = "-fx-background-color: #3b4044;";
				
				((Displayer) e.getSource()).setStyle(style);
				nom.setStyle(style);
				infos.setStyle(style);
				
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				final String style = "-fx-background-color: #242729;";
				
				((Displayer) e.getSource()).setStyle(style);
				nom.setStyle(style);
				infos.setStyle(style);
				
			}
		});
	}
	
	/**
	 * <h1>getNom</h1>
	 * @return nom the label who displays the name
	 */
	public Label getNom() {
		return nom;
	}
	/**
	 * <h1>getImage</h1>
	 * @return image the imageview who displays the preview
	 */
	public ImageView getImage() {
		return image;
	}
	/**
	 * <h1>getInfos</h1>
	 * @return infos the label who displays the others informations.
	 */
	public Label getInfos() {
		return infos;
	}
	
	/**
	 * <p>Warning : may return null if the displayed object isn't a Ressource</p>
	 * @return ressource the displayed resources
	 */
	public Ressource getRessource() {
		return ressource;
	}
	/**
	 * <p>Warning : may return null if the displayed object isn't a TypeAppareil</p>
	 * @return typeAppareil the displayed device
	 */
	public TypeAppareil getTypeAppareil() {
		return typeAppareil;
	}
}
