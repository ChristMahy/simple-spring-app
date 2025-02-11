package cmahy.webapp.user.kernel.application.mapper.output;

import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import jakarta.inject.Named;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class UserSecurityOutputAppVoMapper {

    private final RoleOutputAppVoMapper roleOutputAppVoMapper;

    public UserSecurityOutputAppVoMapper(
        RoleOutputAppVoMapper roleOutputAppVoMapper
    ) {
        this.roleOutputAppVoMapper = roleOutputAppVoMapper;
    }

    public UserSecurityOutputAppVo map(UserSecurity user) {
        if (Objects.isNull(user)) {
            throw new RequiredException(UserSecurity.class);
        }

        return new UserSecurityOutputAppVo(
            Objects.nonNull(user.getId()) ? new UserId(user.getId()) : null,
            user.getUserName(),
            user.getPassword(),
            user.getFullName(),
            user.getStreet(),
            user.getCity(),
            user.getState(),
            user.getZip(),
            user.getPhoneNumber(),
            user.getAuthProvider(),
            user.getExpired(),
            user.getLocked(),
            user.getEnabled(),
            user.getCredentialsExpired(),
            Objects.nonNull(user.getRoles()) ? user.getRoles().stream().map(roleOutputAppVoMapper::map).collect(Collectors.toSet()) : Collections.emptySet()
        );
    }
}
