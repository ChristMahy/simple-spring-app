package cmahy.simple.spring.webapp.user.kernel.exception;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;

public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException(RoleId roleId) {
        super(Role.class.getSimpleName() + ", id <" + roleId.value() + "> not found");
    }

    public RoleNotFoundException(String name) {
        super(Role.class.getSimpleName() + ", name <" + name + "> not found");
    }
}
