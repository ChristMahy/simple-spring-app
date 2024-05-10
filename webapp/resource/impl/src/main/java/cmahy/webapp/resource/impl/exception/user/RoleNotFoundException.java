package cmahy.webapp.resource.impl.exception.user;

import cmahy.webapp.resource.impl.domain.user.Role;
import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.impl.exception.NotFoundException;

public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException(RoleId roleId) {
        super(Role.class.getSimpleName() + ", id <" + roleId.value() + "> not found");
    }

    public RoleNotFoundException(String name) {
        super(Role.class.getSimpleName() + ", name <" + name + "> not found");
    }
}
