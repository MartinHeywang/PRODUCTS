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

	public String getURL() {
		return url;
	}

	public int getValue() {
		return niveau;
	}
}
