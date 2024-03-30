package cmahy.common.entity.exception;

public class NoSuitableConstructorFoundException extends RuntimeException {

    public NoSuitableConstructorFoundException(String message) {
        super(message);
    }

    public NoSuitableConstructorFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
