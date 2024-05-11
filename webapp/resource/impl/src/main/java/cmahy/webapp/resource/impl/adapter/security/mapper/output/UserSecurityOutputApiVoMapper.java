package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.webapp.resource.impl.adapter.user.mapper.id.UserIdMapper;
import cmahy.webapp.resource.impl.adapter.user.mapper.output.RoleOutputApiVoMapper;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.api.vo.output.UserSecurityOutputApiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserSecurityOutputApiVoMapper {

    private final UserIdMapper userIdMapper;
    private final RoleOutputApiVoMapper roleOutputApiVoMapper;

    public UserSecurityOutputApiVoMapper(
        UserIdMapper userIdMapper,
        RoleOutputApiVoMapper roleOutputApiVoMapper
    ) {
        this.userIdMapper = userIdMapper;
        this.roleOutputApiVoMapper = roleOutputApiVoMapper;
    }

    public UserSecurityOutputApiVo map(UserSecurityOutputAppVo output) {
        if (Objects.isNull(output)) {
            throw new NullException(UserSecurityOutputAppVo.class);
        }

        return new UserSecurityOutputApiVo(
            userIdMapper.map(output.id()),
            output.userName(),
            output.password(),
            output.fullName(),
            output.street(),
            output.city(),
            output.state(),
            output.zip(),
            output.phoneNumber(),
            output.authProvider().name(),
            output.isExpired(),
            output.isLocked(),
            output.isEnabled(),
            output.isCredentialsExpired(),
            output.roles().stream().map(roleOutputApiVoMapper::map).collect(Collectors.toSet())
        );
    }
}
