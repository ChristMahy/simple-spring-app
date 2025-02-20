package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.webapp.user.kernel.domain.User;
import cmahy.simple.spring.webapp.user.kernel.domain.id.UserId;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserOutputAppVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UserOutputAppVoMapper {

    public UserOutputAppVo map(User user) {
        if (Objects.isNull(user)) {
            throw new RequiredException(User.class);
        }

        return new UserOutputAppVo(
            Objects.nonNull(user.getId()) ? new UserId(user.getId()) : null,
            user.getUserName(),
            user.getPassword(),
            user.getFullName(),
            user.getStreet(),
            user.getCity(),
            user.getState(),
            user.getZip(),
            user.getPhoneNumber()
        );
    }
}
