package cmahy.simple.spring.webapp.user.kernel.domain.builder;

import cmahy.simple.spring.webapp.user.kernel.domain.*;

import java.util.Collection;

public interface RoleBuilder<R extends Role> {

    RoleBuilder<R> name(String name);

    <U extends User> RoleBuilder<R> users(Collection<U> users);

    <RIGHT extends Right> RoleBuilder<R> rights(Collection<RIGHT> rights);

    R build();
}
