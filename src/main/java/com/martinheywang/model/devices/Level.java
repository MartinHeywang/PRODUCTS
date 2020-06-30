package com.martinheywang.model.devices;

public enum Level {
	LEVEL_1("/devices_level_1/", 1, "Niveau 1"),
	LEVEL_2("/devices_level_2/", 2, "Niveau 2"),
	LEVEL_3("/devices_level_3/", 4, "Niveau 3");

	String url;
	String literal;
	int niveau;

	Level(String url, int niveau, String literal) {
		this.url = url;
		this.niveau = niveau;
		this.literal = literal;
	}

	/**
	 * Returns the next value in this enum. For example, this method
	 * invoked on a LEVEL_1 will return a LEVEL_2. If the level, is
	 * already at maximum, will return the max.
	 * 
	 * @return the next ordinal
	 */
	public Level getNext() {
		final int ordinal = this.ordinal();
		if (Level.values().length <= ordinal + 1) {
			return Level.LEVEL_3;
		} else {
			return Level.values()[ordinal + 1];
		}
	}

	public String getURL() {
		return url;
	}

	public int getValue() {
		return niveau;
	}

	public String getLiteral() {
		return literal;
	}
}
