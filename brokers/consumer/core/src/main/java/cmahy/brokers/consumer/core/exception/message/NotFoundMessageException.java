package cmahy.brokers.consumer.core.exception.message;

import cmahy.brokers.consumer.core.exception.MessageException;

public class NotFoundMessageException extends MessageException {

    public NotFoundMessageException() {
        super("Message not found");
    }
}
