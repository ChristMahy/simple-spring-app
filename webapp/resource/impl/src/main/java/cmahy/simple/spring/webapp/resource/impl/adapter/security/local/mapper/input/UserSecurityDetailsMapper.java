package cmahy.simple.spring.webapp.resource.impl.adapter.security.local.mapper.input;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.local.vo.input.TacoResourceUserDetailsInputVo;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserOutputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSecurityDetailsMapper {

    public TacoResourceUserDetailsInputVo map(UserSecurityOutputAppVo user) {
        if (Objects.isNull(user)) {
            throw new RequiredException(UserOutputAppVo.class);
        }

        return new TacoResourceUserDetailsInputVo(user);
    }
}
