package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RoleBuilder;

public interface RoleBuilderFactory<R extends Role> {

    RoleBuilder<R> create();

    RoleBuilder<R> create(R role);
}
