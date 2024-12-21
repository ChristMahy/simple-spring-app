package cmahy.webapp.user.kernel.application.mapper.input;

import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilder;
import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UserSecurityInputAppVoMapper {

    private final UserSecurityBuilderFactory<UserSecurity> builderFactory;

    public UserSecurityInputAppVoMapper(
        UserSecurityBuilderFactory builderFactory
    ) {
        this.builderFactory = builderFactory;
    }

    public UserSecurity map(UserSecurityInputAppVo input) {
        if (Objects.isNull(input)) {
            throw new RequiredException(UserSecurityInputAppVo.class);
        }

        return builderFactory.create()
            .userName(input.userName())
            .password(input.password())
            .fullName(input.fullName())
            .street(input.street())
            .city(input.city())
            .state(input.state())
            .zip(input.zip())
            .phoneNumber(input.phoneNumber())
            .authProvider(input.authProvider())
            .expired(input.isExpired())
            .locked(input.isLocked())
            .enabled(input.isEnabled())
            .credentialsExpired(input.isCredentialsExpired())
            .build();
    }
}
