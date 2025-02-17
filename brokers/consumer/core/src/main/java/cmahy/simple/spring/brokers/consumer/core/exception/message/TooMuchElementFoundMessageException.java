package cmahy.simple.spring.brokers.consumer.core.exception.message;

public class TooMuchElementFoundMessageException extends MessageException {

    public TooMuchElementFoundMessageException() {
        super("Too much elements found");
    }

    public TooMuchElementFoundMessageException(String message) {
        super(message);
    }
}
