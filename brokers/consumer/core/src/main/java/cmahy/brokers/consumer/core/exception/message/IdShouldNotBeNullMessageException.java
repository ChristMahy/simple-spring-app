package cmahy.brokers.consumer.core.exception.message;

import cmahy.brokers.consumer.core.exception.MessageException;

public class IdShouldNotBeNullMessageException extends MessageException {

    public IdShouldNotBeNullMessageException() {
        super("Id should not be null");
    }
}
