package cmahy.simple.spring.webapp.taco.shop.kernel.exception;

public abstract class DuplicateException extends TacoException {

    public DuplicateException(String message) {
        super(message);
    }
}
