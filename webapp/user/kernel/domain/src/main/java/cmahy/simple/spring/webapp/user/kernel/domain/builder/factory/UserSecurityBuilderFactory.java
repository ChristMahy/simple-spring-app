package cmahy.simple.spring.webapp.user.kernel.domain.builder.factory;

import cmahy.simple.spring.webapp.user.kernel.domain.UserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.UserSecurityBuilder;

public interface UserSecurityBuilderFactory<US extends UserSecurity> {

    UserSecurityBuilder<US> create();

    UserSecurityBuilder<US> create(US userSecurity);
}
