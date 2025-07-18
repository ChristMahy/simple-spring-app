package cmahy.simple.spring.webapp.user.kernel.exception;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;

public class RightNotFoundException extends NotFoundException {

    public RightNotFoundException(RightId rightId) {
        super(Right.class.getSimpleName() + ", id <" + rightId.value() + "> not found");
    }

    public RightNotFoundException(String name) {
        super(Right.class.getSimpleName() + ", name <" + name + "> not found");
    }
}
