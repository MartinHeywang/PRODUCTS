package com.martin.model.appareils;

public enum Direction {
	UP("000", "Haut", 0, -1),
	LEFT("270", "Gauche", -1, 0),
	DOWN("180", "Bas", 0, 1),
	RIGHT("090", "Droite", 1, 0),
	NONE("-1", "None", 0, 0);
	
	private String rotate;
	private String nom;
	private int xPlus, yPlus;
	
	Direction(String rotate, String nom, int xPlus, int yPlus){
		this.rotate = rotate;
		this.nom = nom;
		this.xPlus = xPlus;
		this.yPlus = yPlus;
	}

	/**
	 * @return the rotate
	 */
	public String getRotate() {
		return rotate;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the xPlus
	 */
	public int getxPlus() {
		return xPlus;
	}

	/**
	 * @return the yPlus
	 */
	public int getyPlus() {
		return yPlus;
	}
}
