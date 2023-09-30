package cmahy.brokers.consumer.core.exception;

public class MoreThanOneFoundMessageException extends MessageException {
    public MoreThanOneFoundMessageException() {
        super("Message, more than one element expected");
    }
}
