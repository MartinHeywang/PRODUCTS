package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Stats;
import com.martin.model.Ressource;
import com.martin.view.JeuContr�le;

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
		entr�es.clear();
		sorties.clear();
		switch(rotate) {
		case "000":
			entr�es.add(Direction.DOWN);
			sorties.add(Direction.DOWN);
			break;
		case "090":
			entr�es.add(Direction.RIGHT);
			sorties.add(Direction.RIGHT);
			break;
		case "180":
			entr�es.add(Direction.UP);
			sorties.add(Direction.UP);
			break;
		case "270":
			entr�es.add(Direction.LEFT);
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
				for(int j = 0; j < JeuContr�le.images[X][Y].getAppareil().getEntr�esList().size(); j++) {
				
					if(sorties.get(i) == JeuContr�le.images[X][Y].getAppareil().getEntr�esList().get(j) && ressources.size() > 0) {
						
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
								if(JeuContr�le.images[x][y].getStage().isShowing()) {
									try {
										JeuContr�le.images[x][y].getControler().setLabelSortie(res.getNom());
										JeuContr�le.images[x][y].getControler().setImgSortie(res.getURL());
										ressources.remove(0);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
								}
								else if(JeuContr�le.images[X][Y].getStage().isShowing()) {
									try {
										JeuContr�le.images[X][Y].getControler().setLabelEntr�e(res.getNom());
										JeuContr�le.images[X][Y].getControler().setImgEntr�e(res.getURL());
										ressources.remove(0);
									} catch (FileNotFoundException e) {
										e.printStackTrace();
									}
								}
								ressources.remove(0);
							}
						});
						
						JeuContr�le.images[X][Y].getAppareil().getRessources().add(res);
						
						
						JeuContr�le.argent.set(JeuContr�le.argent.get()-Stats.�lectricit�);
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
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE R�CUP�RATION DES DONN�ES du "
					+ "prix de la presse � fil"+e.getMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}
	
}