package com.martin;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "parties")
public class Partie {

	@DatabaseField(generatedId = true)
	private int idPartie;
	
	@DatabaseField(canBeNull = false)
	private String nom;
	
	@DatabaseField(canBeNull = false)
	private String lastView;
	
	@DatabaseField(canBeNull = false, defaultValue = "1250")
	private long argent;
	
	@DatabaseField(canBeNull = false, defaultValue = "3")
	private int tailleGrille;
	
	public Partie() {}

	public Partie(String nom) throws NullPointerException, SQLException {
		this.nom = nom;
		this.lastView = LocalDateTime.now().toString();
	}
	public Partie(String nom, String lastView) {
		this.nom = nom;
		this.lastView = lastView;
	}
	public void close() throws NullPointerException, SQLException {
		this.lastView = LocalDateTime.now().toString();
		Connect_SQLite.getPartieDao().update(this);
	}
	public void rename(String nom) {
		this.nom = nom;
	}
	
}
