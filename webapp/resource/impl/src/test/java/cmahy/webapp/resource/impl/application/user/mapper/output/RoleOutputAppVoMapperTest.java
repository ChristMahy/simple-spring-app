package cmahy.webapp.resource.impl.application.user.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.Role;
import cmahy.webapp.resource.impl.exception.NullException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RoleOutputAppVoMapperTest {

    @InjectMocks
    private RoleOutputAppVoMapper roleOutputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            Role role = new Role();

            role.setName(Generator.generateAString());
            role.setId(Generator.randomLongEqualOrAboveZero());

            RoleOutputAppVo actual = roleOutputAppVoMapper.map(role);

            assertThat(actual).isNotNull();

            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(role.getId());

            assertThat(actual.name()).isEqualTo(role.getName());
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            roleOutputAppVoMapper.map(null);
        });
    }
}