package com.martin;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "parties")
public class Partie {

	@DatabaseField(generatedId = true, unique = true, columnName = "idPartie")
	private int idPartie;
	
	@DatabaseField(canBeNull = false)
	private String nom;
	
	@DatabaseField(canBeNull = false)
	private String lastView;
	
	@DatabaseField(canBeNull = false, defaultValue = "1250")
	private long argent;
	
	@DatabaseField(canBeNull = false, defaultValue = "3")
	private int tailleGrille;
	
	public Partie() {System.out.println("Constructeur de partie sans args. ID = "+idPartie);}

	public Partie(String nom) throws NullPointerException, SQLException {
		this.nom = nom;
		this.lastView = LocalDateTime.now().toString();
		this.tailleGrille = 3;
		this.argent = 1250;
		Connect_SQLite.getPartieDao().create(this);
		System.out.println("Constructeur de partie arg le nom. ID = "+idPartie);
	}
	public Partie(String nom, String lastView) throws NullPointerException, SQLException {
		this.nom = nom;
		this.lastView = lastView;
		this.tailleGrille = 3;
		Connect_SQLite.getPartieDao().create(this);
		System.out.println("Constructeur de partie args nom + lastView. ID = "+idPartie);
	}
	public void close() throws NullPointerException, SQLException {
		this.lastView = LocalDateTime.now().toString();
		Connect_SQLite.getPartieDao().update(this);
	}
	public void rename(String nom) {
		this.nom = nom;
	}
	public String getNom() {
		return nom;
	}
	public LocalDateTime getLastView() {
		return LocalDateTime.parse(lastView);
	}

	public long getArgent() {
		return argent;
	}

	public void setArgent(long argent) {
		this.argent = argent;
	}

	public int getTailleGrille() {
		return tailleGrille;
	}
	public int getID() {
		System.out.println("getID returned value "+idPartie);
		return idPartie;
	}
	
}
