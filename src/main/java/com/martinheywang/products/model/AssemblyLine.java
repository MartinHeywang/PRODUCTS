package com.martinheywang.products.model;

import java.util.ArrayList;
import java.util.List;

import com.martinheywang.products.model.devices.Device;

/**
 * This class defines an assembly line, with all its devices, and gain, and everything that's calculated in it.
 */
public class AssemblyLine {
    
    private final List<Device> devices = new ArrayList<>();

    public AssemblyLine(List<Device> devices){

        this.devices.addAll(devices);
    }

    public void add(Device device){
        this.devices.add(device);
    }
}
