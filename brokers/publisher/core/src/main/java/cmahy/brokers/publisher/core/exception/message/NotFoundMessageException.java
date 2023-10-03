package cmahy.brokers.publisher.core.exception.message;

public class NotFoundMessageException extends MessageException {

    public NotFoundMessageException() {
        super("Message not found");
    }
}
