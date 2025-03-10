package cmahy.simple.spring.webapp.user.kernel.exception;

public class UserExistsException extends UserException {

    public UserExistsException(String message) {
        super(message);
    }
}
