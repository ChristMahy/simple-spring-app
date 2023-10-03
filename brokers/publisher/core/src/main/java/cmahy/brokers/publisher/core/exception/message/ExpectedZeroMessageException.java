package cmahy.brokers.publisher.core.exception.message;

public class ExpectedZeroMessageException extends MessageException {

    public ExpectedZeroMessageException() {
        super("Found more than zero messages");
    }
}
