package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.RoleStub;
import cmahy.simple.spring.webapp.user.kernel.domain.UserStub;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.UserOutputAppVo;
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
            UserStub user = new UserStub()
                .setId(Generator.randomUUID())
                .setUserName(Generator.generateAString())
                .setPassword(Generator.randomBytes(20))
                .setFullName(Generator.generateAString())
                .setPhoneNumber(Generator.generateAString())
                .setCity(Generator.generateAString())
                .setState(Generator.generateAString())
                .setStreet(Generator.generateAString())
                .setZip(Generator.generateAString());

            user.setRoles(
                Stream.generate(() -> new RoleStub()
                        .setId(Generator.randomUUID())
                        .setName(Generator.generateAString())
                        .setUsers(Set.of(user))
                    )
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
    void map_whenAllPropertiesAreNull_thenResultPropertiesAreNull() {
        assertDoesNotThrow(() -> {
            UserStub user = new UserStub();

            UserOutputAppVo actual = userOutputAppVoMapper.map(user);

            assertThat(actual)
                .isNotNull()
                .hasAllNullFieldsOrProperties();
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {
            userOutputAppVoMapper.map(null);
        });
    }
}