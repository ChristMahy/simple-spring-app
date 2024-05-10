package cmahy.webapp.resource.impl.exception.user;

import cmahy.webapp.resource.impl.exception.ValidationException;

public class UserValidationException extends ValidationException {

    public UserValidationException(String className, String fieldName, String message) {
        super("Validation failed for field <" + fieldName + ">, <" + className + ">: <" + message + ">");
    }
}
