package com.martinheywang.model.behaviours;

import java.util.ArrayList;
import java.util.List;

import com.martinheywang.model.devices.Device;
import com.martinheywang.model.resources.Resource;
import com.martinheywang.view.GameController;

public class Press extends Transform {

	private static List<Resource> acceptedResources = new ArrayList<>();

	public Press(Device device, GameController controller) {
		super(device, controller);
	}

	@Override
	public String getPrefix() {
		return "";
	}

	@Override
	public String getSufix() {
		return "_PLATE";
	}

	// Todo : rename method to 'getAcceptedResources' instead of
	// 'getAcceptedResource'
	@Override
	public List<Resource> getAcceptedResource() {
		return acceptedResources;
	}

	public static void addAcceptedResource(Resource res) {
		acceptedResources.add(res);
	}

	public static void removeAcceptedResource(Resource res) {
		acceptedResources.remove(res);
	}
}