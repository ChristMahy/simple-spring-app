package cmahy.webapp.user.kernel.domain.builder;

import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.User;

import java.util.Collection;

public interface RoleBuilder<R extends Role> {

    RoleBuilder<R> name(String name);

    <U extends User> RoleBuilder<R> users(Collection<U> users);

    R build();
}
