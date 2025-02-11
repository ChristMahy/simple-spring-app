package cmahy.webapp.user.kernel.application.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import cmahy.webapp.user.kernel.domain.UserSecurity;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilder;
import cmahy.webapp.user.kernel.domain.builder.UserSecurityBuilderStub;
import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.input.UserSecurityInputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityInputAppVoMapperTest {

    @Mock
    private UserSecurityBuilderFactory<UserSecurity> builderFactory;

    @InjectMocks
    private UserSecurityInputAppVoMapper userSecurityInputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserSecurityInputAppVo userSecurityInputAppVo = new UserSecurityInputAppVo(
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

            UserSecurityBuilder<? extends UserSecurity> builder = new UserSecurityBuilderStub();

            when(builderFactory.create()).thenAnswer(_ -> builder);

            UserSecurity actual = userSecurityInputAppVoMapper.map(userSecurityInputAppVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("roles", "id", "enabled", "expired", "locked", "credentialsExpired")
                .isEqualTo(userSecurityInputAppVo);

            assertThat(actual.getEnabled()).isEqualTo(userSecurityInputAppVo.isEnabled());
            assertThat(actual.getLocked()).isEqualTo(userSecurityInputAppVo.isLocked());
            assertThat(actual.getExpired()).isEqualTo(userSecurityInputAppVo.isExpired());
            assertThat(actual.getCredentialsExpired()).isEqualTo(userSecurityInputAppVo.isCredentialsExpired());
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {
            userSecurityInputAppVoMapper.map(null);
        });
    }
}