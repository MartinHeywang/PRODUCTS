package com.martin.model.appareils;

public enum Direction {
	UP(0.0d, "Haut", 0, -1),
	LEFT(90.0d, "Gauche", -1, 0),
	DOWN(180.0d, "Bas", 0, 1),
	RIGHT(270.0d, "Droite", 1, 0),
	NONE(220.0d, "None", 0, 0);

	private double rotate;
	private String nom;
	private int xPlus, yPlus;

	Direction(double rotate, String nom, int xPlus, int yPlus) {
		this.rotate = rotate;
		this.nom = nom;
		this.xPlus = xPlus;
		this.yPlus = yPlus;
	}

	/**
	 * @return the rotate
	 */
	public double getRotate() {
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
