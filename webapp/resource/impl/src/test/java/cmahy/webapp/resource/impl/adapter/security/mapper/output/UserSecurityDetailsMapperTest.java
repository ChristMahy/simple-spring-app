package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.user.api.security.vo.output.UserSecurityDetails;
import cmahy.webapp.resource.user.api.vo.id.UserApiId;
import cmahy.webapp.resource.user.api.vo.output.UserSecurityOutputApiVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityDetailsMapperTest {

    @Mock
    private UserSecurityOutputApiVoMapper userSecurityOutputApiVoMapper;

    @InjectMocks
    private UserSecurityDetailsMapper userSecurityDetailsMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserSecurityOutputAppVo appOutput = mock(UserSecurityOutputAppVo.class);
            UserSecurityOutputApiVo user = new UserSecurityOutputApiVo(
                new UserApiId(Generator.randomLongEqualOrAboveZero()),
                Generator.generateAString(),
                Generator.generateAString().getBytes(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.randomEnum(AuthProvider.class).name(),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                SecurityUserGenerator.generateCommonApiRoles()
            );

            when(userSecurityOutputApiVoMapper.map(appOutput)).thenReturn(user);

            UserSecurityDetails actual = userSecurityDetailsMapper.map(appOutput);

            assertThat(actual).isNotNull();

            assertThat(actual.userSecurity()).isEqualTo(user);

            assertThat(actual.getUsername()).isEqualTo(user.userName());
            assertThat(actual.getName()).isEqualTo(user.id().value().toString());
            assertThat(actual.getPassword()).isEqualTo(new String(user.password(), StandardCharsets.UTF_8));
            assertThat(actual.isEnabled()).isEqualTo(Boolean.TRUE.equals(user.isEnabled()));
            assertThat(actual.isAccountNonExpired()).isEqualTo(Boolean.FALSE.equals(user.isExpired()));
            assertThat(actual.isAccountNonLocked()).isEqualTo(Boolean.FALSE.equals(user.isLocked()));
            assertThat(actual.isCredentialsNonExpired()).isEqualTo(Boolean.FALSE.equals(user.isCredentialsExpired()));
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            userSecurityDetailsMapper.map(null);
        });
    }
}