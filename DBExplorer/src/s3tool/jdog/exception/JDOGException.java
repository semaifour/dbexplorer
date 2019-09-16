package s3tool.jdog.exception;

import com.silrais.toolkit.exception.SilraisBaseException;


public class JDOGException extends SilraisBaseException {

    public JDOGException(String message) {
        super(message);
    }

    public JDOGException(String message, Throwable cause) {
        super(message, cause);
    }
}
