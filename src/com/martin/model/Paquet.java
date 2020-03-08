package com.martin.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "paquets")
public class Paquet {

	@DatabaseField(generatedId = true)
	private int idPaquet;

	@DatabaseField
	private Ressource ressource;

	@DatabaseField
	private int quantité;

	public Paquet(Ressource ressource, int quantité) {
		this.ressource = ressource;
		this.quantité = quantité;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public int getQuantité() {
		return quantité;
	}
}
