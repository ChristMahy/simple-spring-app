package cmahy.webapp.user.kernel.domain.builder.factory;

import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilder;

public interface UserSecurityBuilderFactory<US extends UserSecurity> {

    UserSecurityBuilder<US> create();

    UserSecurityBuilder<US> create(US userSecurity);
}
