package cmahy.simple.spring.webapp.taco.shop.kernel.exception;

public abstract class UsageOnDeletionException extends TacoException {

    public UsageOnDeletionException(String message) {
        super(message);
    }
}
