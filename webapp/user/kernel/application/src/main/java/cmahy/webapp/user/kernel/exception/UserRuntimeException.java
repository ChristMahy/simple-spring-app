package cmahy.webapp.user.kernel.exception;

public class UserRuntimeException extends RuntimeException {

    public UserRuntimeException() {
    }

    public UserRuntimeException(String message) {
        super(message);
    }

    public UserRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRuntimeException(Throwable cause) {
        super(cause);
    }

    public UserRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
