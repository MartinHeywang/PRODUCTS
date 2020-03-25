package com.martin.model;

import com.martin.model.appareils.Appareil;

public class Paquet {

	private Long idPaquet;

	private Ressource ressource;

	private int quantité;

	private Appareil appareil;

	public Paquet() {
	}

	public Paquet(Ressource ressource, int quantité) {
		this.ressource = ressource;
		this.quantité = quantité;
	}

	public Paquet(Ressource ressource, int quantité, Appareil appareil) {
		this.ressource = ressource;
		this.quantité = quantité;
		this.appareil = appareil;
	}

	/**
	 * 
	 * @return the id
	 */
	public Long getIdPaquet() {
		return idPaquet;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setIdPaquet(Long id) {
		this.idPaquet = id;
	}

	/**
	 * 
	 * @return the resource
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
	 * @return the quantity
	 */
	public int getQuantité() {
		return quantité;
	}

	/**
	 * 
	 * @param quantité the quantity to set
	 */
	public void setQuantité(int quantité) {
		this.quantité = quantité;
	}

	/**
	 * 
	 * @return the associated-device
	 */
	public Appareil getAppareil() {
		return appareil;
	}

	/**
	 * 
	 * @param appareil the device to set
	 */
	public void setAppareil(Appareil appareil) {
		this.appareil = appareil;
	}

	@Override
	public String toString() {
		return "Object type Paquet. Id : " + idPaquet + ". Ressource : "
				+ ressource + ". Quantité : " + quantité + ".";
	}
}
