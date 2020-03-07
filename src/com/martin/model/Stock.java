package com.martin.model;

import java.util.ArrayList;

import com.martin.model.appareils.Appareil;

public class Stock extends ArrayList<Paquet>{

	private static final long serialVersionUID = 1L;
	
	public Appareil appareil;
	
	public Stock(ArrayList<Paquet> paquets) throws Exception{
		super(paquets);
		final int idAppareil = this.get(0).getAppareil().getID();
		for(int i = 1; i < this.size(); i++) {
			if(idAppareil != this.get(i).getAppareil().getID()) {
				throw new Exception("Les paquets n'appartiennent pas tous au même appareil !");
			}
		}
	}
	
	public void set(String préfixe, int id) {
		final Paquet old = this.get(id);
		this.set(id, new Paquet(Ressource.valueOf(préfixe+old.getRessource().getNom()), 
				old.getQuantité(), appareil));
	}

}