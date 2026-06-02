package cmahy.simple.spring.webapp.resource.integration.test.persistence.application.exception;

public class UnableToGenerateItemException extends Exception {

    public UnableToGenerateItemException(Class<?> type) {
        super(String.format("Unable to generate item for type <%s>", type.getSimpleName()));
    }

    public UnableToGenerateItemException(Class<?> type, Throwable cause) {
        super(String.format("Unable to generate item for type <%s>", type.getSimpleName()), cause);
    }

}
