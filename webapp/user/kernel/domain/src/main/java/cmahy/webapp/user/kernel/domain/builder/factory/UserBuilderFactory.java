package cmahy.webapp.user.kernel.domain.builder.factory;

import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.builder.UserBuilder;

public interface UserBuilderFactory<U extends User> {

    UserBuilder<U> create();

    UserBuilder<U> create(U user);
}
