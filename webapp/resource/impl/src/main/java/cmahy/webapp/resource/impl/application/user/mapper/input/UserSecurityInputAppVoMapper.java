package cmahy.webapp.resource.impl.application.user.mapper.input;

import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;
import cmahy.webapp.resource.impl.exception.NullException;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UserSecurityInputAppVoMapper {

    public UserSecurity map(UserSecurityInputAppVo input) {
        if (Objects.isNull(input)) {
            throw new NullException(UserSecurityInputAppVo.class);
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
