package com.martin;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.martin.model.Coordonnées;
import com.martin.model.appareils.Appareil;
import com.martin.model.appareils.Appareil_Sol;
import com.martin.model.appareils.Direction;
import com.martin.model.appareils.NiveauAppareil;
import com.martin.view.JeuContrôle;

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
	
	private List<? extends Appareil> listAppareils;
	
	public Partie() {}

	public Partie(String nom) throws SQLException {
		this.nom = nom;
		this.lastView = LocalDateTime.now().toString();
		this.tailleGrille = 3;
		this.argent = 1250;
		for(int x = 0; x < tailleGrille; x++) {
			for (int y = 0; y < tailleGrille; y++) {
				try {
					Connect_SQLite.getAppareilDao().createIfNotExists(new Appareil_Sol(new Coordonnées(x, y), Direction.UP, 
							NiveauAppareil.NIVEAU_1, JeuContrôle.get(), this));
				} catch (FileNotFoundException e) {
					System.out.println(e.getLocalizedMessage());
					
				}
			}
		}
		listAppareils = Connect_SQLite.getAppareilDao().queryBuilder().where().eq("partie_idPartie", idPartie)
				.query();
		
		this.save();
	}
	public Partie(String nom, String lastView) throws SQLException {
		this.nom = nom;
		this.lastView = lastView;
		this.tailleGrille = 3;
		
		for(int x = 0; x < tailleGrille; x++) {
			for (int y = 0; y < tailleGrille; y++) {
				try {
					Connect_SQLite.getAppareilDao().createIfNotExists(new Appareil_Sol(new Coordonnées(x, y), Direction.UP, 
							NiveauAppareil.NIVEAU_1, JeuContrôle.get(), this));
				} catch (FileNotFoundException e) {
					System.out.println(e.getLocalizedMessage());
					
				}
			}
		}
		listAppareils = Connect_SQLite.getAppareilDao().queryBuilder().where().eq("partie_idPartie", idPartie)
				.query();
		
		this.save();
	}
	
	public void rename(String newName) {
		this.nom = newName;
	}
	public void save() {
		try {
			Connect_SQLite.getPartieDao().createOrUpdate(this);
			for(int i = 0; i < this.getAppareils().size(); i++) {
				Connect_SQLite.getAppareilDao().createOrUpdate(this.getAppareils().get(i));
			}
		} catch (SQLException e) {
			System.err.println("La partie n'a pas pu être sauvegardée.");
			
		}
		// Todo : save method
	}
	public void delete() {
		// Todo : delete method
	}
	
	public int getID() {
		return idPartie;
	}
	public String getNom() {
		return nom;
	}
	public long getArgent() {
		return argent;
	}
	public int getTailleGrille() {
		return tailleGrille;
	}
	public List<? extends Appareil> getAppareils(){
		try {
			listAppareils = Connect_SQLite.getAppareilDao().queryBuilder().where().eq("partie_idPartie", idPartie)
					.query();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			
		}
		return listAppareils;
	}
	public Appareil getAppareilsWithCoordinates(Coordonnées xy) throws NullPointerException{
		try {
			return Connect_SQLite.getAppareilDao().queryBuilder().where()
					.eq("partie_idPartie", idPartie).and()
					.eq("xy_idCoordonnées", xy.getID())
					.queryForFirst();
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());
			return null;
		}
	}
	public LocalDateTime getLastView() {
		return LocalDateTime.parse(lastView);
	}
	
	public boolean setArgent(long argent, boolean increase){
		if(increase) {
			this.argent += argent;
			return true;
		}
		else {
			if(this.argent > argent) {
				this.argent -= argent;
				return true;
			}
			else {
				return false;
			}
		}
	}
	
}
