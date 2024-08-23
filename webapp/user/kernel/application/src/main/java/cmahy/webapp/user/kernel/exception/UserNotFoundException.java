package cmahy.webapp.user.kernel.exception;

import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(UserId id) {
        super(User.class.getSimpleName() + ", id <" + id.value() + "> not found");
    }

    public UserNotFoundException(String username) {
        super(User.class.getSimpleName() + ", username <" + username + "> not found");
    }
}
