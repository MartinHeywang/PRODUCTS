package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement_Assembleur;
import com.martin.model.appareils.orientation.Entrées_LeftAndCenter;
import com.martin.model.appareils.orientation.Sorties_Right;
import com.martin.view.JeuContrôle;

import javafx.beans.property.SimpleIntegerProperty;

public class Appareil_Assembleur extends Appareil {

	Ressource produit = Ressource.NONE;
	
	ArrayList<Ressource> recette = new ArrayList<Ressource>();
	
	private static SimpleIntegerProperty prix;
	
	public Appareil_Assembleur(Coordonnées xy, NiveauAppareil niveau, Direction direction, JeuContrôle controller) throws FileNotFoundException {
		super(xy, TypeAppareil.ASSEMBLEUR, direction, niveau, controller);
		
		entrées = new Entrées_LeftAndCenter();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Right();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Assembleur(xy, niveau, pointerExit.getxPlus(), 
				pointerExit.getyPlus(), controller);
	}
	
	@Override
	public void destruction() {
		try {
			Connect_SQLite.getInstance().createStatement().executeUpdate(
					"UPDATE appareils_infos SET '"+(xy.getX()+1)+"' = 'NONE' WHERE id = '"+(xy.getY()+1)+"';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setProduit(Ressource res) {
		this.produit = res;
		
		try {
			Connect_SQLite.getInstance().createStatement().executeUpdate("UPDATE appareils_infos "
					+ "SET '"+(xy.getX()+1)+"' = \""+res.toString()+"\" WHERE id = "+(xy.getY()+1)+";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Martin
	 * 26 janv. 2020 | 11:46:55
	 * 
	 * <b>checkIngrédients()</b>
	 * <p>Vérifie si les ressources de l'appareil sont suffisantes pour créer l'objet de la recette</p>
	 * 
	 * Return type :
	 * @return boolean
	 * 
	*/
	private boolean checkIngrédients() {
		//La liste de stock permet de stocker temporairement les ressources supprimée de la liste de recette
		ArrayList<Ressource> stock = new ArrayList<Ressource>();
		//Antant de fois que la longueur de la recette
		for(int j = 0; j < recette.size(); j++) {
			/*Si la ressource demandée par la recette est trouvée, on l'enlève de ressources et on la stock 
			 * temporairement dans stock.
			*/
			if(ressources.contains(recette.get(j))) {
				stock.add(recette.get(j));
				ressources.remove(recette.get(j));
			}
			else {
				//Sinon on replace les ressources de stock dans ressources
				ressources.addAll(stock);
				if(pointerExit.isInGrid(controller.getTailleGrille())) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	/**
	 * @author Heywang
	 * 24/01/2020
	 * 
	 * 
	 * Rôle : Execute l'action de l'assembleur, c'est-à-dire, vérifier si les ressources nécessaires au schéma 
	 * sont disponibles.
	 * 
	 * @param resATraiter -> objet Ressource avec lequel la méthode va travailler
	 * */
	public void action(Ressource resATraiter) {
		//Boucle répétée autant de fois que le niveau de l'appareil le permet
		for(int level = 0; level < niveau.getNiveau(); level++) {
			recette = new ArrayList<Ressource>();
				
			/*On ajoute dans la liste de recette les ingrédients nécessaire (en quantité aussi
			, d'où les boucles)*/
			for(int i = 0; i<produit.getRecette().get(0).getQuantité(); i++) {
				recette.add(produit.getRecette().get(0).getRessource());
			}
			for(int i = 0; i<produit.getRecette().get(1).getQuantité(); i++) {
				recette.add(produit.getRecette().get(1).getRessource());
			}
			
			if(checkIngrédients()) {
					
					if(pointerExit.isInGrid(controller.getTailleGrille())) {
						for(int i = 0; i< sorties.size(); i++) {
							for(int j = 0; j < controller.getGrilleAppareils(pointerExit.getX(), 
									pointerExit.getY()
									).getEntrées().size(); j++) {
								
								if(sorties.get(i) == controller.getGrilleAppareils(xy.getX(), xy.getY())
										.getEntrées().get(j)) {
									controller.getGrilleAppareils(pointerExit.getX(), pointerExit.getY())
									.action(produit);
									
								}
							}
					}
				}
			}
			}
		
	}
	public static void initializeData() {
		try {
			prix = new SimpleIntegerProperty(Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT * FROM infos").getInt("prixAssembleur"));
		} catch (SQLException e) {
			System.out.println("ERREUR LORS D'UNE TENTATIVE DE RÉCUPÉRATION DES DONNÉES du "
					+ "prix de l'assembleur"+e.getMessage());
			prix = new SimpleIntegerProperty(999_999_999);
		}
	}
	public static int getPrix() {
		return prix.get();
	}
}
