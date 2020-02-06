package com.martin.model;

public class Paquet {

	private Ressource ressource;
	private int quantité;
	
	public Paquet() {
		this.ressource = Ressource.NONE;
		this.quantité = 0;
	}
	
	public Paquet(Ressource res, int qté) {
		this.ressource = res;
		this.quantité = qté;
	}
	
	public Ressource getRessource() {
		return ressource;
	}
	public void setRessource(Ressource res) {
		this.ressource = res;
	}
	
	public int getQuantité() {
		return quantité;
	}
	public void setQuantité(int quantité) {
		this.quantité = quantité;
	}

}
