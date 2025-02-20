package cmahy.simple.spring.webapp.taco.shop.kernel.exception;

public class RequiredException extends TacoException {

    public RequiredException(Class<?> type) {
        super(type.getName() + " has not to be null");
    }
}
