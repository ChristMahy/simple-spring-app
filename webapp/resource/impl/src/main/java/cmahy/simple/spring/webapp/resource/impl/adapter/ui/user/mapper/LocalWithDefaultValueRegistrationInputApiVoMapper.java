package cmahy.simple.spring.webapp.resource.impl.adapter.ui.user.mapper;

import cmahy.simple.spring.webapp.resource.ui.vo.input.RegistrationInputUiVo;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LocalWithDefaultValueRegistrationInputApiVoMapper {

    private final PasswordEncoder passwordEncoder;

    public LocalWithDefaultValueRegistrationInputApiVoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserSecurityInputAppVo map(RegistrationInputUiVo input) {
        if (Objects.isNull(input)) {
            throw new RequiredException(RegistrationInputUiVo.class);
        }

        return new UserSecurityInputAppVo(
            input.username(),
            passwordEncoder.encode(input.password()).getBytes(StandardCharsets.UTF_8),
            input.fullName(),
            input.street(),
            input.city(),
            input.state(),
            input.zip(),
            input.phone(),
            AuthProvider.LOCAL,
            false,
            false,
            true,
            false
        );
    }
}
