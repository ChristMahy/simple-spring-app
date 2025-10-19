package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.exception;

public class MySqlTestContainerException extends RuntimeException {

    public MySqlTestContainerException(String message) {
        super(message);
    }

    public MySqlTestContainerException(String message, Throwable cause) {
        super(message, cause);
    }
}
