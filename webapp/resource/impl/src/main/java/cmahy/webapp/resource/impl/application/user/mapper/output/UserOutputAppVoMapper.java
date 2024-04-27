package cmahy.webapp.resource.impl.application.user.mapper.output;

import cmahy.webapp.resource.impl.application.user.vo.output.UserOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.User;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UserOutputAppVoMapper {

    public UserOutputAppVo map(User user) {
        if (Objects.isNull(user)) {
            throw new NullException(User.class);
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
