package cmahy.webapp.taco.shop.kernel.exception;

public class TacoException extends Exception {

    public TacoException() {
    }

    public TacoException(String message) {
        super(message);
    }

    public TacoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TacoException(Throwable cause) {
        super(cause);
    }

    public TacoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
