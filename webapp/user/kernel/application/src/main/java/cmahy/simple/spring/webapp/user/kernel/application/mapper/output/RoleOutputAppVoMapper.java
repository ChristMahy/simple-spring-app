package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RoleOutputAppVo;
import jakarta.inject.Named;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class RoleOutputAppVoMapper {

    private final RightOutputAppVoMapper rightOutputAppVoMapper;

    public RoleOutputAppVoMapper(RightOutputAppVoMapper rightOutputAppVoMapper) {
        this.rightOutputAppVoMapper = rightOutputAppVoMapper;
    }

    public RoleOutputAppVo map(Role role) {

        if (Objects.isNull(role)) {
            throw new RequiredException(Role.class);
        }

        return new RoleOutputAppVo(
            Objects.nonNull(role.getId()) ? new RoleId(role.getId()) : null,
            role.getName(),
            Objects.nonNull(role.getRights()) ? role.getRights().stream().map(rightOutputAppVoMapper::map).collect(Collectors.toSet()) : Collections.emptySet()
        );

    }
}
