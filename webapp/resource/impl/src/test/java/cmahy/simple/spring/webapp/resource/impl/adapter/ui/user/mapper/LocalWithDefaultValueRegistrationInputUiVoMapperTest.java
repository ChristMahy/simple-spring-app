package cmahy.simple.spring.webapp.resource.impl.adapter.ui.user.mapper;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.ui.vo.input.RegistrationInputUiVo;
import cmahy.simple.spring.webapp.user.kernel.domain.AuthProvider;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalWithDefaultValueRegistrationInputUiVoMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LocalWithDefaultValueRegistrationInputApiVoMapper localWithDefaultValueRegistrationInputApiVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            RegistrationInputUiVo inputApiVo = new RegistrationInputUiVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString()
            );

            when(passwordEncoder.encode(inputApiVo.password())).thenReturn(inputApiVo.password());

            UserSecurityInputAppVo actual = localWithDefaultValueRegistrationInputApiVoMapper.map(inputApiVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("userName", "password", "phoneNumber", "authProvider", "isExpired", "isLocked", "isEnabled", "isCredentialsExpired")
                        .build()
                )
                .isEqualTo(inputApiVo);

            assertThat(actual.userName()).isEqualTo(inputApiVo.username());
            assertThat(actual.password()).isEqualTo(inputApiVo.password().getBytes(StandardCharsets.UTF_8));
            assertThat(actual.phoneNumber()).isEqualTo(inputApiVo.phone());
            assertThat(actual.authProvider()).isEqualTo(AuthProvider.LOCAL);
            assertThat(actual.isExpired()).isEqualTo(false);
            assertThat(actual.isLocked()).isEqualTo(false);
            assertThat(actual.isEnabled()).isEqualTo(true);
            assertThat(actual.isCredentialsExpired()).isEqualTo(false);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(RequiredException.class, () -> {
            localWithDefaultValueRegistrationInputApiVoMapper.map(null);
        });
    }
}