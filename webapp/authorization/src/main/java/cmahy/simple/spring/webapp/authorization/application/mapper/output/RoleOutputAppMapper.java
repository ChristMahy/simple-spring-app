package cmahy.simple.spring.webapp.authorization.application.mapper.output;

import cmahy.simple.spring.webapp.authorization.application.vo.output.RoleOutputAppVo;
import cmahy.simple.spring.webapp.authorization.domain.Role;
import cmahy.simple.spring.webapp.authorization.domain.id.RoleId;
import jakarta.inject.Named;

@Named
public class RoleOutputAppMapper {

    public RoleOutputAppVo map(Role role) {
        return new RoleOutputAppVo(
            new RoleId(role.getId()),
            role.getName()
        );
    }
}
