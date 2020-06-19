package com.martinheywang.model.devices;

public enum Level {
	LEVEL_1("/devices_level_1/", 1),
	LEVEL_2("/devices_level_2/", 2),
	LEVEL_3("/devices_level_3/", 3);

	String url;
	int niveau;

	Level(String url, int niveau) {
		this.url = url;
		this.niveau = niveau;
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
		if (Level.values().length < ordinal + 1) {
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
}
