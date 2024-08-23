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

import java.util.List;
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
            UserSecurity userSecurity = new UserSecurity();

            userSecurity.setId(Generator.randomLongEqualOrAboveZero());
            userSecurity.setUserName(Generator.generateAString());
            userSecurity.setFullName(Generator.generateAString());
            userSecurity.setPassword(Generator.randomBytes(20));
            userSecurity.setPhoneNumber(Generator.generateAString());
            userSecurity.setCity(Generator.generateAString());
            userSecurity.setState(Generator.generateAString());
            userSecurity.setStreet(Generator.generateAString());
            userSecurity.setZip(Generator.generateAString());
            userSecurity.setRoles(
                Stream.generate(() -> {
                        Role role = new Role();

                        role.setId(Generator.randomLongEqualOrAboveZero());
                        role.setName(Generator.generateAString());
                        role.setUsers(List.of(userSecurity));

                        when(roleOutputAppVoMapper.map(role)).thenAnswer(_ -> mock(RoleOutputAppVo.class));

                        return role;
                    })
                    .limit(Generator.randomInt(10, 20))
                    .toList()
            );
            userSecurity.setAuthProvider(Generator.randomEnum(AuthProvider.class));
            userSecurity.setEnabled(Generator.randomBoolean());
            userSecurity.setExpired(Generator.randomBoolean());
            userSecurity.setLocked(Generator.randomBoolean());
            userSecurity.setCredentialsExpired(Generator.randomBoolean());

            UserSecurityOutputAppVo actual = userSecurityOutputAppVoMapper.map(userSecurity);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("roles", "id")
                        .build()
                )
                .isEqualTo(userSecurity);

            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(userSecurity.getId());

            assertThat(actual.roles()).hasSize(userSecurity.getRoles().size());
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {
            userSecurityOutputAppVoMapper.map(null);
        });
    }
}