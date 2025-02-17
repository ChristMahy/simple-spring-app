package cmahy.simple.spring.brokers.consumer.core.exception.message;

public class DuplicateMessageException extends MessageException {

    public DuplicateMessageException() {
        super("Too much messages found");
    }

    public DuplicateMessageException(String message) {
        super(message);
    }
}
