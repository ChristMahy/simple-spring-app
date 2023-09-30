package cmahy.brokers.consumer.core.exception;

public class ExpectedZeroMessageException extends MessageException {

    public ExpectedZeroMessageException() {
        super("Found more than zero messages");
    }
}
