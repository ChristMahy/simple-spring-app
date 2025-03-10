package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.user.kernel.domain.Role;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RoleId;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RoleOutputAppVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class RoleOutputAppVoMapper {

    public RoleOutputAppVo map(Role role) {
        if (Objects.isNull(role)) {
            throw new RequiredException(Role.class);
        }

        return new RoleOutputAppVo(
            Objects.nonNull(role.getId()) ? new RoleId(role.getId()) : null,
            role.getName()
        );
    }
}
