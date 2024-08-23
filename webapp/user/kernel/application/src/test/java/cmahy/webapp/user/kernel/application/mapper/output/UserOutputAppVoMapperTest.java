package cmahy.webapp.user.kernel.application.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.output.UserOutputAppVo;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserOutputAppVoMapperTest {

    @InjectMocks
    private UserOutputAppVoMapper userOutputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            User user = new User();

            user.setId(Generator.randomLongEqualOrAboveZero());
            user.setUserName(Generator.generateAString());
            user.setPassword(Generator.randomBytes(20));
            user.setFullName(Generator.generateAString());
            user.setPhoneNumber(Generator.generateAString());
            user.setCity(Generator.generateAString());
            user.setState(Generator.generateAString());
            user.setStreet(Generator.generateAString());
            user.setZip(Generator.generateAString());
            user.setRoles(
                Stream.generate(() -> {
                    Role role = new Role();

                    role.setId(Generator.randomLongEqualOrAboveZero());
                    role.setName(Generator.generateAString());
                    role.setUsers(Set.of(user));

                    return role;
                })
                    .limit(Generator.randomInt(5, 10))
                    .toList()
            );

            UserOutputAppVo actual = userOutputAppVoMapper.map(user);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id")
                        .build()
                )
                .isEqualTo(user);

            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(user.getId());
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {
            userOutputAppVoMapper.map(null);
        });
    }
}