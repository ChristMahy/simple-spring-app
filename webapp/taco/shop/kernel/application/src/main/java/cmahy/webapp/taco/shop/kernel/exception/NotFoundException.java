package cmahy.webapp.taco.shop.kernel.exception;

public abstract class NotFoundException extends TacoException {

    public NotFoundException(String message) {
        super(message);
    }
}
