package com.martinheywang.model.behaviours;

import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.devices.Device;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.GameController;

public final class Furnace extends Transform {

	private static final List<Resource> acceptedResource = new ArrayList<>();

	public Furnace(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public String getPrefix() {
		return "";
	}

	@Override
	public String getSufix() {
		return "_INGOT";
	}

	@Override
	public List<Resource> getAcceptedResource() {
		return acceptedResource;
	}

	/**
	 * <p>
	 * Add a resource to the 'acceptedResource'.
	 * </p>
	 * <p>
	 * <strong> This resource must have a 'friend' that has the same name
	 * as it, but with the prefix and the suffix of the devices. This
	 * method won't give a warning but you'll get an error if this
	 * condition is not respected.</strong>
	 * </p>
	 * 
	 * @param resource the resource to add
	 */
	public static void addAcceptedResource(Resource resource) {
		acceptedResource.add(resource);
	}

	/**
	 * Removes an accepted resource.
	 * 
	 * @param resource the resource to remove
	 */
	public static void removeAcceptedResource(Resource resource) {
		acceptedResource.remove(resource);
	}

}
