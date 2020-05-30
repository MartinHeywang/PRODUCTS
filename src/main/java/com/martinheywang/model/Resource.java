package com.martinheywang.model;

import java.util.ArrayList;
import java.util.List;

public interface Resource {

	List<Resource> resourceReferences = new ArrayList<Resource>();

	public String getName();

	public long getPrice();

	public String getURL();

	public List<Pack> getRecipe();

	public static void addReferenceResource(Resource resource) {
		if (!resourceReferences.contains(resource)) {
			resourceReferences.add(resource);
		} else {
			System.out.println(
					"WARNING: This resource was already added in the list.");
		}
	}

	public static List<Resource> getReferences() {
		return resourceReferences;
	}
}
