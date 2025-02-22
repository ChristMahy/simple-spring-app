package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.simple.spring.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.output.UserOutputAppVo;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSecurityDetailsMapper {

    public UserSecurityDetails map(UserSecurityOutputAppVo user) {
        if (Objects.isNull(user)) {
            throw new RequiredException(UserOutputAppVo.class);
        }

        return new UserSecurityDetails(user);
    }
}
