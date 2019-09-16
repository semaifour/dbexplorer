package com.silrais.webtoolkit.interceptor;

import com.silrais.toolkit.exception.SilraisBaseException;

public class InterceptorException extends SilraisBaseException {

	private static final long serialVersionUID = -5673963042255977057L;

	public InterceptorException() {
	        super();
	    }
	    
	    public InterceptorException(String message) {
	    	super(message);
	    }
	    
	    public InterceptorException(Throwable cause) {
	    	super(cause);
	    }

	    public InterceptorException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
