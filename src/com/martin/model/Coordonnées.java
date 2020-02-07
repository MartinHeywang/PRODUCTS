package com.martin.model;

/**
 * @author Heywang
 * 24/01/2020
 * 
 * Rôle : objet regroupant des valeurs x et y. Utilisé par les appareils.
 *
 */
public class Coordonnées {
	
	/**
	 * @serialField x and y*/
	private int x, y;
	
	/**
	 * @author Heywang
	 * 24/01/2020
	 * 
	 * @category constructor
	 * 
	 * Rôle : Crée un objet Coordonnées*/
	public Coordonnées(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @author Heywang
	 * 24/01/2020
	 * 
	 * @category getter
	 * 
	 * Rôle : @return le champ x représenté par cet objet Coordonnées*/
	public int getX() {
		return x;
	}
	/**
	 * @author Heywang
	 * 
	 * @category getter
	 * @return le champ y représenté par cet objet Coordonnées
	 */
	public int getY() {
		return y;
	}
	/**
	 * 
	 * @param tailleMax
	 * @return si les coordonnées se situent toutes les deux entre 0 et la taille passée en paramètre
	 */
	public boolean isInGrid(int tailleMax) {
		if(x < 0 || y < 0 || x > tailleMax || y > tailleMax) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param coordonnées
	 * @return si l'objet Coordonnées en paramètre et cet objet lui-même se situe près l'un de l'autre
	 */
	public boolean isNearFrom(Coordonnées coordonnées){
		if(this.x == coordonnées.getX()+1 || this.x == coordonnées.getX()-1){
			return true;
		}
		if(this.y == coordonnées.getY()+1 || this.x == coordonnées.getY()-1){
			return true;
		}
		return false;
	}
}
