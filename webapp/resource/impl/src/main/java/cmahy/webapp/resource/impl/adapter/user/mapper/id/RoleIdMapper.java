package cmahy.webapp.resource.impl.adapter.user.mapper.id;

import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.api.vo.id.RoleApiId;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoleIdMapper {

    public RoleApiId map(RoleId roleId) {
        if (Objects.isNull(roleId)) {
            throw new NullException(UserId.class);
        }

        return new RoleApiId(
            roleId.value()
        );
    }
}
