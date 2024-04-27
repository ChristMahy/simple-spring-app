package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.security.vo.UserSecurityDetails;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

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
                Generator.randomBoolean()
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
        assertThrows(NullException.class, () -> {
            userSecurityDetailsMapper.map(null);
        });
    }
}