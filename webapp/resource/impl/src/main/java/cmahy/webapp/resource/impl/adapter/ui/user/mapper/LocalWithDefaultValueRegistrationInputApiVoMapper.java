package cmahy.webapp.resource.impl.adapter.ui.user.mapper;

import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.ui.vo.input.RegistrationInputUiVo;
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
            throw new NullException(RegistrationInputUiVo.class);
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
