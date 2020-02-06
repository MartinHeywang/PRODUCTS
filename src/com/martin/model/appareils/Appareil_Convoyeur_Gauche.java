package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Stats;
import com.martin.view.JeuContrôle;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Convoyeur_Gauche extends Appareil {
	
	int X = 0, Y = 0;
	
	private static SimpleIntegerProperty prix;

	public Appareil_Convoyeur_Gauche(int x, int y, String rotate) {
		super(x, y, rotate);
		prix.bind(Appareil_Convoyeur.getPrix());
		
		checkRotation(rotate);
	}
	
	public void checkRotation(String rotate) {
		entrées.clear();
		sorties.clear();
		switch(rotate) {
		case "000":
			entrées.add(Direction.DOWN);
			sorties.add(Direction.LEFT);
			break;
		case "090":
			entrées.add(Direction.LEFT);
			sorties.add(Direction.UP);
			break;
		case "180":
			entrées.add(Direction.UP);
			sorties.add(Direction.RIGHT);
			break;
		case "270":
			entrées.add(Direction.RIGHT);
			sorties.add(Direction.DOWN);
			break;
		}
	}

	@Override
	public void action() {
		for(int k = 0; k < niveau; k++) {
		for(int i = 0; i < sorties.size(); i++) {
			
			
			switch(rotate) {
			case "000":
				X = this.x-1;
				Y = this.y;
				break;
			case "090":
				X = this.x;
				Y = this.y+1;
				break;
			case "180":
				X = this.x+1;
				Y = this.y;
				break;
			case "270":
				X = this.x;
				Y = this.y-1;
				break;
			}
			if(X>-1 && Y>-1 && X<Stats.largeurGrille && Y<Stats.longueurGrille) {
				for(int j = 0; j < JeuContrôle.images[X][Y].getAppareil().getEntréesList().size(); j++) {
					
					Platform.runLater(new Runnable() {
						public void run() {
							if(JeuContrôle.images[x][y].getStage().isShowing()) {
								try {
									JeuContrôle.images[x][y].getControler().setLabelSortie(ressources.get(0).getNom());
									JeuContrôle.images[x][y].getControler().setImgSortie(ressources.get(0).getURL());
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}
							else if(JeuContrôle.images[X][Y].getStage().isShowing()) {
								try {
									JeuContrôle.images[X][Y].getControler().setLabelEntrée(ressources.get(0).getNom());
									JeuContrôle.images[X][Y].getControler().setImgEntrée(ressources.get(0).getURL());
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}
							ressources.remove(0);
						}
					});
					
					if(sorties.get(i) == JeuContrôle.images[X][Y].getAppareil().getEntréesList().get(j) && ressources.size() > 0) {
						JeuContrôle.images[X][Y].getAppareil().getRessources().add(ressources.get(0));
						
						JeuContrôle.argent.set(JeuContrôle.argent.get()-Stats.électricité);
					}
				}
			}
		}
		
		}
	}

	@Override 
	public void destroy() {}
	
	public static void initializeData() {
		prix = new SimpleIntegerProperty();
		prix.bind(Appareil_Convoyeur.prix);
	}
	public static int getPrix() {
		return prix.get();
	}
	

}
