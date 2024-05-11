package cmahy.webapp.resource.impl.adapter.user.mapper.output;

import cmahy.webapp.resource.impl.adapter.user.mapper.id.RoleIdMapper;
import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.vo.output.RoleOutputApiVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoleOutputApiVoMapper {

    private final RoleIdMapper roleIdMapper;

    public RoleOutputApiVoMapper(RoleIdMapper roleIdMapper) {
        this.roleIdMapper = roleIdMapper;
    }

    public RoleOutputApiVo map(RoleOutputAppVo output) {
        if (Objects.isNull(output)) {
            throw new NullException(RoleOutputAppVo.class);
        }

        return new RoleOutputApiVo(
            roleIdMapper.map(output.id()),
            output.name()
        );
    }
}
