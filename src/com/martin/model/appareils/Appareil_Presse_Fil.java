package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Stats;
import com.martin.model.Ressource;
import com.martin.view.JeuContrôle;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Presse_Fil extends Appareil {
	
	int X = 0, Y = 0;
	Ressource res = Ressource.NONE;
	
	private static SimpleIntegerProperty prix;

	public Appareil_Presse_Fil(int x, int y, String rotate) {
		super(x, y, rotate);
		
		prix = new SimpleIntegerProperty(1500);
		
		
		checkRotation(rotate);
	}
	
	public void checkRotation(String rotate) {
		entrées.clear();
		sorties.clear();
		switch(rotate) {
		case "000":
			entrées.add(Direction.DOWN);
			sorties.add(Direction.DOWN);
			break;
		case "090":
			entrées.add(Direction.RIGHT);
			sorties.add(Direction.RIGHT);
			break;
		case "180":
			entrées.add(Direction.UP);
			sorties.add(Direction.UP);
			break;
		case "270":
			entrées.add(Direction.LEFT);
			sorties.add(Direction.LEFT);
			break;
		}
	}

	@Override
	public void action() {
		for(int k = 0; k < niveau; k++) {
		for(int i = 0; i < sorties.size(); i++) {
			switch(rotate) {
			case "000":
				X = this.x;
				Y = this.y+1;
				break;
			case "090":
				X = this.x-1;
				Y = this.y;
				break;
			case "180":
				X = this.x;
				Y = this.y-1;
				break;
			case "270":
				X = this.x+1;
				Y = this.y;
				break;
			}
			if(X>-1 && Y>-1 && X<Stats.largeurGrille && Y<Stats.longueurGrille) {
				for(int j = 0; j < JeuContrôle.images[X][Y].getAppareil().getEntréesList().size(); j++) {
				
					if(sorties.get(i) == JeuContrôle.images[X][Y].getAppareil().getEntréesList().get(j) && ressources.size() > 0) {
						
						switch(ressources.get(0).getNom()) {
						case "Fer":
							res = Ressource.FIL_DE_FER;
							break;
						case "Or":
							res = Ressource.FIL_DE_OR;
							break;
						case "Cuivre":
							res = Ressource.FIL_DE_CUIVRE;
							break;
						case "Argent":
							res = Ressource.FIL_DE_ARGENT;
							break;
						case "Aluminium":
							res = Ressource.FIL_DE_ALUMINIUM;
							break;
							
						default :
							res = Ressource.NONE;
						}
						
						Platform.runLater(new Runnable() {
							public void run() {
								if(JeuContrôle.images[x][y].getStage().isShowing()) {
									try {
										JeuContrôle.images[x][y].getControler().setLabelSortie(res.getNom());
										JeuContrôle.images[x][y].getControler().setImgSortie(res.getURL());
										ressources.remove(0);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
								}
								else if(JeuContrôle.images[X][Y].getStage().isShowing()) {
									try {
										JeuContrôle.images[X][Y].getControler().setLabelEntrée(res.getNom());
										JeuContrôle.images[X][Y].getControler().setImgEntrée(res.getURL());
										ressources.remove(0);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
								}
								ressources.remove(0);
							}
						});
						
						JeuContrôle.images[X][Y].getAppareil().getRessources().add(res);
						
						
						JeuContrôle.argent.set(JeuContrôle.argent.get()-Stats.électricité);
					}
				}
			}
		}
		}
	}

	
	@Override public void destroy() {}
	
	public static void initializeData() {
		try {
			prix = new SimpleIntegerProperty(Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixPresseFil"));
		} catch (SQLException e) {
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE RÉCUPÉRATION DES DONNÉES du "
					+ "prix de la presse à fil"+e.getMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}
	
}