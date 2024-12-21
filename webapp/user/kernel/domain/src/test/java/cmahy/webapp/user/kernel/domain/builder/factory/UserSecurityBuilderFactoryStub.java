package cmahy.webapp.user.kernel.domain.builder.factory;

import cmahy.webapp.user.kernel.domain.UserSecurityStub;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilderStub;

public class UserSecurityBuilderFactoryStub implements UserSecurityBuilderFactory<UserSecurityStub> {

    @Override
    public UserSecurityBuilderStub create() {
        return new UserSecurityBuilderStub();
    }

    @Override
    public UserSecurityBuilderStub create(UserSecurityStub userSecurity) {
        return new UserSecurityBuilderStub(userSecurity);
    }
}
