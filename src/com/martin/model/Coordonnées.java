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
	 * Rôle : @return x and y fields*/
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isInGrid(int tailleMax) {
		if(x < 0 || y < 0 || x > tailleMax || y > tailleMax) {
			return false;
		}
		return true;
	}
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
