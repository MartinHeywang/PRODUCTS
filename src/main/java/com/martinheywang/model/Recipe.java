package com.martinheywang.model;

import java.math.BigInteger;

import com.martinheywang.model.resources.Resource;

public class Recipe {

	private Pack[] packs;

	public Recipe() {
	}

	public Recipe(Pack[] packs) {
		this.packs = packs;
	}

	public Recipe(String[] packs) throws IllegalArgumentException {

		this.packs = new Pack[packs.length];

		for (int i = 0; i < packs.length; i++) {
			final Resource resource;
			final BigInteger quantity;

			final String[] parts = packs[i].split("|");

			if (parts.length != 2) {
				throw new IllegalArgumentException(
						"The Recipe couldn't be created, "
								+ "this String was not parsable: "
								+ packs[i]);
			}

			quantity = new BigInteger(parts[0]);
			resource = Resource.valueOf(parts[1]);

			this.packs[i] = new Pack(resource, quantity);
		}
	}

	public Pack[] getPacks() {
		return packs;
	}

	public void setPack(Pack pack, int index) {
		this.packs[index] = pack;
	}

}
