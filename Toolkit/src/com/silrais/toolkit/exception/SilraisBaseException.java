package com.silrais.toolkit.exception;

public class SilraisBaseException extends Exception {

    public SilraisBaseException() {
        super();
    }
    
    public SilraisBaseException(String message) {
    	super(message);
    }
    
    public SilraisBaseException(Throwable cause) {
    	super(cause);
    }

    public SilraisBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
