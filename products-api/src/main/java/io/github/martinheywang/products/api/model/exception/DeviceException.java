/*
   Copyright 2021 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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