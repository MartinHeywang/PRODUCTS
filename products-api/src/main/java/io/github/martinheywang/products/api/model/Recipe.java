package io.github.martinheywang.products.api.model;

public class Recipe {

	private Pack[] packs;

	public Recipe() {
	}

	public Recipe(Pack[] packs) {
		this.packs = packs;
	}

	public Pack[] getPacks() {
		return packs;
	}

	public void setPack(Pack pack, int index) {
		this.packs[index] = pack;
	}

}
