package cmahy.simple.spring.brokers.consumer.core.exception.message;

public class NotFoundMessageException extends MessageException {

    public NotFoundMessageException() {
        super("Message not found");
    }
}
