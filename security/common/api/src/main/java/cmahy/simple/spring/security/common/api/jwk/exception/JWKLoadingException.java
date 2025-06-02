package cmahy.simple.spring.security.common.api.jwk.exception;

public class JWKLoadingException extends Exception {

    public JWKLoadingException(String message) {
        super(message);
    }

    public JWKLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

}
