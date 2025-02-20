package cmahy.simple.spring.webapp.user.kernel.exception;

public class UserValidationException extends ValidationException {

    public UserValidationException(String className, String fieldName, String message) {
        super("Validation failed for field <" + fieldName + ">, <" + className + ">: <" + message + ">");
    }
}
