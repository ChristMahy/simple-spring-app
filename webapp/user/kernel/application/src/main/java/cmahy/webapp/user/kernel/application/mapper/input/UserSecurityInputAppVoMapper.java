package cmahy.webapp.user.kernel.application.mapper.input;

import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UserSecurityInputAppVoMapper {

    public UserSecurity map(UserSecurityInputAppVo input) {
        if (Objects.isNull(input)) {
            throw new RequiredException(UserSecurityInputAppVo.class);
        }

        UserSecurity userSecurity = new UserSecurity();

        userSecurity.setUserName(input.userName());
        userSecurity.setPassword(input.password());
        userSecurity.setFullName(input.fullName());
        userSecurity.setStreet(input.street());
        userSecurity.setCity(input.city());
        userSecurity.setState(input.state());
        userSecurity.setZip(input.zip());
        userSecurity.setPhoneNumber(input.phoneNumber());
        userSecurity.setAuthProvider(input.authProvider());
        userSecurity.setExpired(input.isExpired());
        userSecurity.setLocked(input.isLocked());
        userSecurity.setEnabled(input.isEnabled());
        userSecurity.setCredentialsExpired(input.isCredentialsExpired());

        return userSecurity;
    }
}
