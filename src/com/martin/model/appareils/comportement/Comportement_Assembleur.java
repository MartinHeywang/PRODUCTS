package com.martin.model.appareils.comportement;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.model.Coordonnées;
import com.martin.model.Ressource;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

import javafx.scene.paint.Color;

public class Comportement_Assembleur implements Comportement {

	private Coordonnées pointer;
	private NiveauAppareil niveau;
	private JeuContrôle controller;
	
	private Ressource produit = Ressource.NONE;
	
	public Comportement_Assembleur(Coordonnées xy, NiveauAppareil niveau, 
			int xToAdd, int yToAdd, JeuContrôle controller) {
		this.niveau = niveau;
		this.controller = controller;
		this.pointer = new Coordonnées(xy.getX()+xToAdd, xy.getY()+yToAdd);
		
		try {
			produit = Ressource.valueOf(Connect_SQLite.getInstance().prepareStatement(
					"SELECT * FROM appareils_infos WHERE id = "+(xy.getX()+1)+";").executeQuery()
					.getString(""+(xy.getY()+1)+""));
		} catch (SQLException e) {
			controller.setReport("Attention : un assembleur n'a pas pu charger la ressource qu'il doit distribuer", Color.ORANGE.darker());
		}
	}

	@Override
	public void action(Ressource resATraiter) throws NegativeArgentException {
		// TODO Attention méthode encore non réalisée.
	}

}
