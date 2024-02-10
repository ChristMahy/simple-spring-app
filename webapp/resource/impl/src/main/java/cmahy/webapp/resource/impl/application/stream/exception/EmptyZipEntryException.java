package cmahy.webapp.resource.impl.application.stream.exception;

public class EmptyZipEntryException extends RuntimeException {

    public EmptyZipEntryException() {
        super("Entry should not be empty");
    }
}
