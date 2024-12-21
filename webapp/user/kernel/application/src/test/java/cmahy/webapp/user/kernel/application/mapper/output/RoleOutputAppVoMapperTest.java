package cmahy.webapp.user.kernel.application.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.user.kernel.domain.Role;
import cmahy.webapp.user.kernel.domain.RoleStub;
import cmahy.webapp.user.kernel.exception.RequiredException;
import cmahy.webapp.user.kernel.vo.output.RoleOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleOutputAppVoMapperTest {

    @InjectMocks
    private RoleOutputAppVoMapper roleOutputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            Role role = new RoleStub()
                .setId(Generator.randomLongEqualOrAboveZero())
                .setName(Generator.generateAString());

            RoleOutputAppVo actual = roleOutputAppVoMapper.map(role);

            assertThat(actual).isNotNull();

            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(role.getId());

            assertThat(actual.name()).isEqualTo(role.getName());
        });
    }

    @Test
    void map_whenAllPropertiesAreNull_thenResultPropertiesAreNull() {
        assertDoesNotThrow(() -> {
            Role role = new RoleStub();

            RoleOutputAppVo actual = roleOutputAppVoMapper.map(role);

            assertThat(actual)
                .isNotNull()
                .hasAllNullFieldsOrProperties();
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {
            roleOutputAppVoMapper.map(null);
        });
    }
}