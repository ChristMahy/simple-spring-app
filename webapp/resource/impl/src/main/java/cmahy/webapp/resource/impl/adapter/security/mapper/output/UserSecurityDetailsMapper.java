package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.webapp.resource.impl.adapter.security.vo.UserSecurityDetails;
import cmahy.webapp.resource.impl.application.user.vo.output.UserOutputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.exception.NullException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSecurityDetailsMapper {

    public UserSecurityDetails map(UserSecurityOutputAppVo user) {
        if (Objects.isNull(user)) {
            throw new NullException(UserOutputAppVo.class);
        }

        return new UserSecurityDetails(user);
    }
}
