package cmahy.simple.spring.brokers.consumer.core.exception.message;

public class IdShouldNotBeNullMessageException extends MessageException {

    public IdShouldNotBeNullMessageException() {
        super("Id should not be null");
    }
}
