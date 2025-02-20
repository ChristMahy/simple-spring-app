package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserBuilderStub;

public class UserBuilderFactoryStub implements UserBuilderFactory<UserStub> {

    @Override
    public UserBuilderStub create() {
        return new UserBuilderStub();
    }

    @Override
    public UserBuilderStub create(UserStub user) {
        return new UserBuilderStub(user);
    }
}
