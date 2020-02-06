package com.martin.model.appareils.comportement;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Comportement_Acheteur implements Comportement {
	
	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	private Ressource resDistribuée;
	
	public Comportement_Acheteur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller){
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
		
		try {
			resDistribuée = Ressource.valueOf(Connect_SQLite.getInstance().prepareStatement(
					"SELECT * FROM appareils_infos WHERE id = "+(xy.getX()+1)+";").executeQuery()
					.getString(""+(xy.getY()+1)+""));
		} catch (SQLException e) {
			System.out.println("ERREUR dans Comportement_Acheteur dans la méthode "
					+ "Comportement_Acheteur. Raison :\n" + e.getLocalizedMessage());
		}
	}
	
	@Override
	public void action(Ressource resATraiter) {
		for(int niveau = 0; this.niveau.getNiveau() == niveau+1; niveau++) {
			try {
				controller.getGrilleAppareils(pointer).action(resDistribuée);
				controller.setArgent(5+Appareil.getÉlectricité(), false);
			} catch (NegativeArgentException e) {
				System.out.println("ERREUR dans Comportement_Acheteur dans la méthode action. Raison :\n"
						+ e.getLocalizedMessage());
			}
		}
	}	
}