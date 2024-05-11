package cmahy.webapp.resource.impl.adapter.user.mapper.id;

import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.api.vo.id.UserApiId;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserIdMapper {

    public UserApiId map(UserId userId) {
        if (Objects.isNull(userId)) {
            throw new NullException(UserId.class);
        }

        return new UserApiId(
            userId.value()
        );
    }
}
