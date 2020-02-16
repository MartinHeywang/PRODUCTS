package com.martin.model.appareils.comportement;

import java.sql.SQLException;
import java.util.ArrayList;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

import javafx.scene.paint.Color;

public class Comportement_Assembleur implements Comportement {

	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	private Ressource[] produit = {Ressource.NONE};
	private ArrayList<Ressource> ressources = new ArrayList<Ressource>();
	private ArrayList<Ressource> recette = new ArrayList<Ressource>();
	
	public Comportement_Assembleur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
		
		try {
			produit[0] = Ressource.valueOf(Connect_SQLite.getInstance().prepareStatement(
					"SELECT * FROM appareils_infos WHERE id = "+(xy.getX()+1)+";").executeQuery()
					.getString(""+(xy.getY()+1)+""));
		} catch (SQLException e) {
			controller.setReport("Attention : un assembleur n'a pas pu charger la ressource qu'il doit distribuer", Color.ORANGE.darker());
		}
	}
	@Override
	public void action(Ressource[] resATraiter) throws NegativeArgentException{
		Ressource[] toSend = new Ressource[niveau.getNiveau()];
		
		for(int level = 0; level < niveau.getNiveau(); level++) {
			if(controller.getArgentProperty().get() < 5+Appareil.getÉlectricité())
				throw new NegativeArgentException("Le comportement d'un acheteur "
						+ "n'a pas pu être réalisé car le solde d'argent n'était pas assez important.");
			
			if(checkIngrédients()) {
				toSend[level] = produit[0];
				controller.setArgent(Appareil.getÉlectricité(), false);
			}
		}
		
		controller.getGrilleAppareils(pointer).action(toSend);
	}
	
	/**
	 * 
	 * <h1>checkIngrédients</h1>
	 * <p>Vérifie si les ressources de l'appareil sont suffisantes pour créer l'objet de la recette</p>
	 * 
	 * @return boolean if the requires resources are available
	 * 
	*/
	private boolean checkIngrédients() {
		ArrayList<Ressource> stock = new ArrayList<Ressource>();
		recette = new ArrayList<Ressource>();
		for(int i = 0; i<produit[0].getRecette().get(0).getQuantité(); i++) {
			recette.add(produit[0].getRecette().get(0).getRessource());
		}
		for(int i = 0; i<produit[0].getRecette().get(1).getQuantité(); i++) {
			recette.add(produit[0].getRecette().get(1).getRessource());
		}
		
		for(int j = 0; j < produit[0].getRecette().size(); j++) {
			if(ressources.contains(recette .get(j))) {
				stock.add(recette.get(j));
				ressources.remove(recette.get(j));
			}
			else {
				ressources.addAll(stock);
				if(pointer.isInGrid(controller.getTailleGrille())) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * <h1>setProduit</h1>
	 * <p>Sets the products to the new value, after checking if it is a valid resource.</p>
	 * @param produit the resource to set
	 */
	public void setProduit(Ressource produit) {
		switch(produit) {
		case FER: 
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			break;
		default:
			this.produit[0] = produit;
		}
			
	}
}
