package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserBuilder;

public interface UserBuilderFactory<U extends User> {

    UserBuilder<U> create();

    UserBuilder<U> create(U user);
}
