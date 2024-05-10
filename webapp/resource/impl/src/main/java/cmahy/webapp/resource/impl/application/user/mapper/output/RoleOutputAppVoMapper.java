package cmahy.webapp.resource.impl.application.user.mapper.output;

import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.Role;
import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class RoleOutputAppVoMapper {

    public RoleOutputAppVo map(Role role) {
        if (Objects.isNull(role)) {
            throw new NullException(Role.class);
        }

        return new RoleOutputAppVo(
            Objects.nonNull(role.getId()) ? new RoleId(role.getId()) : null,
            role.getName()
        );
    }
}
