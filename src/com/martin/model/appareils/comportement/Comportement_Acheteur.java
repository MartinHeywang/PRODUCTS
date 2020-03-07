package com.martin.model.appareils.comportement;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Acheteur implements Comportement {
	
	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	private Appareil appareil;
	
	private Stock resDistribuée;
	
	public Comportement_Acheteur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller, Appareil appareil){
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
		this.appareil = appareil;
		
		// FixMe : charger la ressource
	}
	
	@Override
	public void action(Stock resATraiter) throws NegativeArgentException{
		for(int niveau = 0; this.niveau.getNiveau() == niveau+1; niveau++) {
			if(controller.getArgent() < 5+Appareil.getÉlectricité())
				throw new NegativeArgentException("Le comportement d'un acheteur "
						+ "n'a pas pu être réalisé car le solde d'argent n'était pas assez important.");
			controller.getGrilleAppareils(pointer).action(resDistribuée);
			if(!resDistribuée.get(0).getRessource().equals(Ressource.NONE))
				controller.setArgent(5+Appareil.getÉlectricité(), false);
		}
	}
	
	/**
	 * <h1>setProduit</h1>
	 * <p>Sets the products to the new value, after checking if it is a valid resource.</p>
	 * @param produit the resource to set
	 */
	public void setProduit(Paquet resDistribuée) {
		switch(resDistribuée.getRessource()) {
		case FER: 
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			for(int i = 0; i < this.niveau.getNiveau(); i++) {
				
				try {
					Connect_SQLite.getPaquetDao().createIfNotExists(resDistribuée);
					this.resDistribuée.add(resDistribuée);
				} catch (SQLException e) {
					// Catch bloc automatiquement généré
					System.err.println(e.getLocalizedMessage());
					
				}
			}
		default:
			break;
		}
			
	}
}