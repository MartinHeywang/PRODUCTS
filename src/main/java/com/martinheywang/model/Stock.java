package com.martinheywang.model;

import java.util.ArrayList;

public class Stock extends ArrayList<Pack> {

	private static final long serialVersionUID = 1L;

	public Stock(ArrayList<Pack> packings) throws Exception {
		super(packings);
	}

	public Stock() {
		super();
	}

	public void add(BaseResources resource) {
		this.add(new Pack(resource, 1));
	}

}