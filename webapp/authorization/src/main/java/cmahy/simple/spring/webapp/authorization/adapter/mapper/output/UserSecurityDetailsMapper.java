package cmahy.simple.spring.webapp.authorization.adapter.mapper.output;

import cmahy.simple.spring.webapp.authorization.adapter.vo.output.UserSecurityDetails;
import cmahy.simple.spring.webapp.authorization.application.vo.output.UserOutputAppVo;
import jakarta.inject.Named;

@Named
public class UserSecurityDetailsMapper {

    public UserSecurityDetails map(UserOutputAppVo user) {
        return new UserSecurityDetails(user);
    }
}
