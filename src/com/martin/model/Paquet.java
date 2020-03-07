package com.martin.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.model.appareils.Appareil;

@DatabaseTable(tableName = "paquets")
public class Paquet{
	
	
	@DatabaseField(generatedId = true)
	private int idPaquet;
	
	@DatabaseField
	private Ressource ressource;
	
	@DatabaseField
	private int quantité;
	
	@DatabaseField(canBeNull = false, foreign = true, foreignColumnName = "idAppareil")
	private Appareil appareil;
	
	public Paquet(Ressource ressource,int quantité, Appareil appareil) {
		this.ressource = ressource;
		this.quantité = quantité;
	}
	
	public Ressource getRessource() {
		return ressource;
	}
	public int getQuantité() {
		return quantité;
	}
	public Appareil getAppareil() {
		return appareil;
	}
}
