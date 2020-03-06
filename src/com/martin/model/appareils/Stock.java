package com.martin.model.appareils;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.Connect_SQLite;
import com.martin.model.Ressource;

@DatabaseTable(tableName = "stock_appareils")
public class Stock {
	
	@DatabaseField(generatedId = true)
	private int idStock;
	
	@DatabaseField(columnName = "ressource")
	private Ressource ressource;
	
	@DatabaseField(columnName = "idAppareil", foreign = true, foreignColumnName = "idAppareil")
	private Appareil appareil;
	
	public Stock() {}
	public Stock(Ressource ressource, Appareil appareil) {
		this.ressource = ressource;
		this.appareil = appareil;
	}
	
	private void save() {
		try {
			Connect_SQLite.getStockDao().createOrUpdate(this);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			
		}
	}
	
	public int getID() {
		return idStock;
	}
	public Ressource getRessource() {
		return ressource;
	}
	public Appareil getAppareil() {
		return appareil;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
		this.save();
	}

}
