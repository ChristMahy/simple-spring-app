package cmahy.brokers.consumer.core.exception;

public class MessageMoreThanOneFoundMessageException extends MessageException {
    public MessageMoreThanOneFoundMessageException() {
        super("Message, more than one element expected");
    }
}
