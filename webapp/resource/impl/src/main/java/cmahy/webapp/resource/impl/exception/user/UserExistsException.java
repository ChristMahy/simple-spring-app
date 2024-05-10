package cmahy.webapp.resource.impl.exception.user;

import cmahy.webapp.resource.impl.exception.ResourceApplicationException;

public class UserExistsException extends ResourceApplicationException {

    public UserExistsException(String message) {
        super(message);
    }
}
