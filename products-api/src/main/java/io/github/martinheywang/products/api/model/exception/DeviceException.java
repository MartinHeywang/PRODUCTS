package io.github.martinheywang.products.api.model.exception;

import java.text.MessageFormat;

import io.github.martinheywang.products.api.model.device.Device;

public final class DeviceException extends Exception{

	/**
	 *
	 */
    private static final long serialVersionUID = -3032694476706679280L;
    
    public DeviceException(){
        super("Une erreur inconnue s'est produite avec un appareil.");
    }

    public DeviceException(String message){
        super(message);
    }

    public DeviceException(Device device){
        super(MessageFormat.format("Une erreur s'est produite en {0}.", device.getPosition()));
    }
    
}