package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.helper.security.user.SecurityUserGenerator;
import cmahy.webapp.resource.ui.vo.output.UserSecurityDetails;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.id.UserId;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
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

    @InjectMocks
    private UserSecurityDetailsMapper userSecurityDetailsMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserSecurityOutputAppVo user = new UserSecurityOutputAppVo(
                new UserId(Generator.randomLongEqualOrAboveZero()),
                Generator.generateAString(),
                Generator.generateAString().getBytes(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.randomEnum(AuthProvider.class),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                SecurityUserGenerator.generateCommonAppRoles()
            );

            UserSecurityDetails actual = userSecurityDetailsMapper.map(user);

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
        assertThrows(RequiredException.class, () -> {
            userSecurityDetailsMapper.map(null);
        });
    }
}