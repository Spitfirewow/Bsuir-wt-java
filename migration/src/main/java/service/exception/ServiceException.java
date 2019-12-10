package service.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = -1279922368196692984L;
    private static final String PREFIX = "SERVICE: ";

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(PREFIX + message);
    }

    public ServiceException(String message, Throwable cause) {
        super(PREFIX + message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
