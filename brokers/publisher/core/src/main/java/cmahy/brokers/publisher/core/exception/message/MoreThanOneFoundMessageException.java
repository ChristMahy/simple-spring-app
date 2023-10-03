package cmahy.brokers.publisher.core.exception.message;

public class MoreThanOneFoundMessageException extends MessageException {
    public MoreThanOneFoundMessageException() {
        super("Message, more than one element expected");
    }
}
