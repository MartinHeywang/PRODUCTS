package com.martinheywang.model;

import java.util.ArrayList;

public class Stock extends ArrayList<Packing> {

	private static final long serialVersionUID = 1L;

	public Stock(ArrayList<Packing> packings) throws Exception {
		super(packings);
	}

	public Stock() {
		super();
	}

	public void add(Resource resource) {
		this.add(new Packing(resource, 1));
	}

}