package com.martin.model.appareils.comportement;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

import javafx.scene.paint.Color;

public class Comportement_Acheteur implements Comportement {
	
	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	private Ressource[] resDistribuée = {Ressource.NONE};
	
	public Comportement_Acheteur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller, int idAppareil){
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
		
		try {
			resDistribuée[0] = Connect_SQLite.getAppareilDao().queryForId(idAppareil).getStock1();
		} catch (SQLException e) {
			controller.setReport("Attention : un acheteur n'a pas pu charger la ressource qu'il doit distribuer", Color.ORANGE.darker());
		}
	}
	
	@Override
	public void action(Ressource[] resATraiter) throws NegativeArgentException{
		for(int niveau = 0; this.niveau.getNiveau() == niveau+1; niveau++) {
			if(controller.getArgent() < 5+Appareil.getÉlectricité())
				throw new NegativeArgentException("Le comportement d'un acheteur "
						+ "n'a pas pu être réalisé car le solde d'argent n'était pas assez important.");
			controller.getGrilleAppareils(pointer).action(resDistribuée);
			if(!resDistribuée[0].equals(Ressource.NONE))
				controller.setArgent(5+Appareil.getÉlectricité(), false);
		}
	}
	
	/**
	 * <h1>setProduit</h1>
	 * <p>Sets the products to the new value, after checking if it is a valid resource.</p>
	 * @param produit the resource to set
	 */
	public void setProduit(Ressource resDistribuée) {
		switch(resDistribuée) {
		case FER: 
		case OR:
		case CUIVRE:
		case ARGENT:
		case DIAMANT:
		case ALUMINIUM:
			this.resDistribuée[0] = resDistribuée;
		default:
			break;
		}
			
	}
}