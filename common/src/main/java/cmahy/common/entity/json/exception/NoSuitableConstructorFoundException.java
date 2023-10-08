package cmahy.common.entity.json.exception;

public class NoSuitableConstructorFoundException extends RuntimeException {

    public NoSuitableConstructorFoundException() {
        this("No suitable constructor found");
    }

    public NoSuitableConstructorFoundException(String message) {
        super(message);
    }
}
