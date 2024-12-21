package cmahy.webapp.user.kernel.domain.builder.factory;

import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.builder.RoleBuilder;

public interface RoleBuilderFactory<R extends Role> {

    RoleBuilder<R> create();

    RoleBuilder<R> create(R role);
}
