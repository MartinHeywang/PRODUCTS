package io.github.martinheywang.products.api.model.exception;

import io.github.martinheywang.products.api.persistance.Persistable;

/**
 * A RequestException should be thrown whenever an error occurs with the
 * persistance system. A detailed message should be passed as argument when
 * creating it.
 */
public class RequestException extends Exception {

    private static final long serialVersionUID = -2000699419892513641L;

    public RequestException(Persistable object){
        super("The request on '"+object+"' could not be performed.");
    }

    public RequestException(Persistable object, String message){
        super("The request on '"+object+"' could not be performed. "+message);
    }

}