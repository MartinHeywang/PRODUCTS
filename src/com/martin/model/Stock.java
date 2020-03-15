package com.martin.model;

import java.util.ArrayList;

public class Stock extends ArrayList<Paquet> {

	private static final long serialVersionUID = 1L;

	public Stock(ArrayList<Paquet> paquets) throws Exception {
		super(paquets);
	}

	public Stock() {
		super();
	}

	public void add(Ressource ressource) {
		this.add(new Paquet(ressource, 1));
	}

}