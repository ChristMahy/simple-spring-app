package cmahy.simple.spring.webapp.authorization.application.mapper.output;

import cmahy.simple.spring.webapp.authorization.application.vo.output.RoleOutputAppVo;
import cmahy.simple.spring.webapp.authorization.domain.Role;
import cmahy.simple.spring.webapp.authorization.domain.id.RoleId;
import jakarta.inject.Named;

import java.util.stream.Collectors;

@Named
public class RoleOutputAppMapper {

    private final RightOutputAppMapper rightOutputAppMapper;

    public RoleOutputAppMapper(RightOutputAppMapper rightOutputAppMapper) {
        this.rightOutputAppMapper = rightOutputAppMapper;
    }

    public RoleOutputAppVo map(Role role) {
        return new RoleOutputAppVo(
            new RoleId(role.getId()),
            role.getName(),
            role
                .getRights().stream()
                .map(rightOutputAppMapper::map)
                .collect(Collectors.toSet())
        );
    }
}
