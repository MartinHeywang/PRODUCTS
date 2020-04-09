package com.martin.model.appareils;

import com.martin.model.Coordonnees;
import com.martin.model.Partie;

public class AppareilModel {

	private Long idAppareilModel;

	private Coordonnees coordonnees;
	private Partie partie;

	private Type type;
	private Niveau niveau;
	private Direction direction;

	public AppareilModel(Coordonnees coordonnees, Partie partie, Type type,
			Niveau niveau, Direction direction) {
		this.coordonnees = coordonnees;
		this.partie = partie;
		this.type = type;
		this.niveau = niveau;
		this.direction = direction;
	}

	/**
	 * @return the idAppareilModel
	 */
	public Long getIdAppareilModel() {
		return idAppareilModel;
	}

	/**
	 * @param idAppareilModel the idAppareilModel to set
	 */
	public void setIdAppareilModel(Long idAppareilModel) {
		this.idAppareilModel = idAppareilModel;
	}

	/**
	 * @return the coordonnees
	 */
	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	/**
	 * @param coordonnees the coordonnees to set
	 */
	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	/**
	 * @return the partie
	 */
	public Partie getPartie() {
		return partie;
	}

	/**
	 * @param partie the partie to set
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the niveau
	 */
	public Niveau getNiveau() {
		return niveau;
	}

	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
