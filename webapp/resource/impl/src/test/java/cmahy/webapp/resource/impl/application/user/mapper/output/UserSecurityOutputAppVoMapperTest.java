package cmahy.webapp.resource.impl.application.user.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.*;
import cmahy.webapp.resource.impl.exception.NullException;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserSecurityOutputAppVoMapperTest {

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
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            userSecurityOutputAppVoMapper.map(null);
        });
    }
}