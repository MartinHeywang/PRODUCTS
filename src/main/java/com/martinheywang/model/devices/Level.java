package com.martinheywang.model.devices;

public enum Level {
	NIVEAU_1("/devices_level_1/", 1),
	NIVEAU_2("/devices_level_2/", 2),
	NIVEAU_3("/devices_level_3/", 3);

	String url;
	int niveau;

	Level(String url, int niveau) {
		this.url = url;
		this.niveau = niveau;
	}

	public String getURL() {
		return url;
	}

	public int getNiveau() {
		return niveau;
	}

	public Level getLevelSup(Level level) {
		if (level == NIVEAU_1)
			return NIVEAU_2;
		if (level == NIVEAU_2)
			return NIVEAU_3;
		else
			return NIVEAU_3;
	}
}
