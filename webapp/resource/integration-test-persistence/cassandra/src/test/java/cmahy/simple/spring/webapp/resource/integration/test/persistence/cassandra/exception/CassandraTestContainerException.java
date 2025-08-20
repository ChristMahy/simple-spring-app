package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.exception;

public class CassandraTestContainerException extends RuntimeException {

    public CassandraTestContainerException(String message) {
        super(message);
    }

    public CassandraTestContainerException(String message, Throwable cause) {
        super(message, cause);
    }

}
