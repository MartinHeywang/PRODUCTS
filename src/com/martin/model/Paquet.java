package com.martin.model;

public class Paquet {

	private Ressource ressource;
	private int quantité;
	
	public Paquet() {
		this.ressource = Ressource.NONE;
		this.quantité = 0;
	}
	
	/**
	 * <h1>constructor Paquet</h1>
	 * 
	 * @param res a resource 
	 * @param qté its quantity
	 */
	public Paquet(Ressource res, int qté) {
		this.ressource = res;
		this.quantité = qté;
	}
	
	/**
	 * 
	 * @return resource the resource
	 */
	public Ressource getRessource() {
		return ressource;
	}
	/**
	 * 
	 * @param res the resource to set
	 */
	public void setRessource(Ressource res) {
		this.ressource = res;
	}
	
	/**
	 * 
	 * @return quantité the quantity
	 */
	public int getQuantité() {
		return quantité;
	}
	/**
	 * 
	 * @param quantité the quantity to change
	 */
	public void setQuantité(int quantité) {
		this.quantité = quantité;
	}

}
