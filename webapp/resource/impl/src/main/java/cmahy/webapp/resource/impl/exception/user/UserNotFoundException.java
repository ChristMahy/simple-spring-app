package cmahy.webapp.resource.impl.exception.user;

import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(UserId id) {
        super(User.class.getSimpleName() + ", id <" + id.value() + "> not found");
    }

    public UserNotFoundException(String username) {
        super(User.class.getSimpleName() + ", username <" + username + "> not found");
    }
}
