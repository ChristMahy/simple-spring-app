package cmahy.webapp.resource.impl.adapter.user.mapper.id;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.api.vo.id.RoleApiId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RoleIdMapperTest {

    @InjectMocks
    private RoleIdMapper roleIdMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            RoleId roleId = new RoleId(Generator.randomLongEqualOrAboveZero());

            RoleApiId actual = roleIdMapper.map(roleId);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(roleId);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            roleIdMapper.map(null);
        });
    }
}