package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.webapp.resource.impl.application.user.vo.output.UserOutputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSecurityDetailsMapper {

    private final UserSecurityOutputApiVoMapper userSecurityOutputApiVoMapper;

    public UserSecurityDetailsMapper(UserSecurityOutputApiVoMapper userSecurityOutputApiVoMapper) {
        this.userSecurityOutputApiVoMapper = userSecurityOutputApiVoMapper;
    }

    public UserSecurityDetails map(UserSecurityOutputAppVo user) {
        if (Objects.isNull(user)) {
            throw new NullException(UserOutputAppVo.class);
        }

        return new UserSecurityDetails(
            userSecurityOutputApiVoMapper.map(
                user
            )
        );
    }
}
