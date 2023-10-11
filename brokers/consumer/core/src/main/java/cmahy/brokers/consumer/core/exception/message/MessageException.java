package cmahy.brokers.consumer.core.exception.message;

public class MessageException extends RuntimeException {
    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
