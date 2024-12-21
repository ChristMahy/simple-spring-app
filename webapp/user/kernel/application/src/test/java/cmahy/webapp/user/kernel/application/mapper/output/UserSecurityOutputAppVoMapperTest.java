package cmahy.webapp.user.kernel.application.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.*;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.output.RoleOutputAppVo;
import cmahy.webapp.user.kernel.vo.output.UserSecurityOutputAppVo;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityOutputAppVoMapperTest {

    @Mock
    private RoleOutputAppVoMapper roleOutputAppVoMapper;

    @InjectMocks
    private UserSecurityOutputAppVoMapper userSecurityOutputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserSecurityStub userSecurity = new UserSecurityStub()
                .setId(Generator.randomLongEqualOrAboveZero())
                .setUserName(Generator.generateAString())
                .setFullName(Generator.generateAString())
                .setPassword(Generator.randomBytes(20))
                .setPhoneNumber(Generator.generateAString())
                .setCity(Generator.generateAString())
                .setState(Generator.generateAString())
                .setStreet(Generator.generateAString())
                .setZip(Generator.generateAString())
                .setAuthProvider(Generator.randomEnum(AuthProvider.class))
                .setEnabled(Generator.randomBoolean())
                .setExpired(Generator.randomBoolean())
                .setLocked(Generator.randomBoolean())
                .setCredentialsExpired(Generator.randomBoolean());

            userSecurity.setRoles(
                Stream.generate(() -> {
                        RoleStub role = mock(RoleStub.class);

                        when(roleOutputAppVoMapper.map(role)).thenAnswer(_ -> mock(RoleOutputAppVo.class));

                        return role;
                    })
                    .limit(Generator.randomInt(10, 20))
                    .toList()
            );

            UserSecurityOutputAppVo actual = userSecurityOutputAppVoMapper.map(userSecurity);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("roles", "id", "isCredentialsExpired", "isLocked", "isEnabled", "isExpired")
                        .build()
                )
                .isEqualTo(userSecurity);

            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(userSecurity.getId());

            assertThat(actual.roles()).hasSize(userSecurity.getRoles().size());

            assertThat(actual.isCredentialsExpired()).isEqualTo(userSecurity.getCredentialsExpired());
            assertThat(actual.isLocked()).isEqualTo(userSecurity.getLocked());
            assertThat(actual.isEnabled()).isEqualTo(userSecurity.getEnabled());
            assertThat(actual.isExpired()).isEqualTo(userSecurity.getExpired());
        });
    }

    @Test
    void map_whenAllPropertiesAreNull_thenResultPropertiesAreNull() {
        assertDoesNotThrow(() -> {
            UserSecurityStub userSecurity = new UserSecurityStub();

            UserSecurityOutputAppVo actual = userSecurityOutputAppVoMapper.map(userSecurity);

            assertThat(actual)
                .isNotNull()
                .hasAllNullFieldsOrPropertiesExcept("roles");

            assertThat(actual.roles()).isEmpty();
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {
            userSecurityOutputAppVoMapper.map(null);
        });
    }
}