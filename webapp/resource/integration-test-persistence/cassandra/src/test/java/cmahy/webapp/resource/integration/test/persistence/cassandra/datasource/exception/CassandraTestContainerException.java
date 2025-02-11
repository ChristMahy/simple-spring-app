package cmahy.webapp.resource.integration.test.persistence.cassandra.datasource.exception;

public class CassandraTestContainerException extends Exception {

    public CassandraTestContainerException(String message) {
        super(message);
    }

    public CassandraTestContainerException(String message, Throwable cause) {
        super(message, cause);
    }
}
