package cmahy.webapp.resource.impl.adapter.user.mapper.id;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.id.UserApiId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserApiIdMapperTest {

    @InjectMocks
    private UserApiIdMapper userApiIdMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserApiId userApiId = new UserApiId(Generator.randomLongEqualOrAboveZero());

            UserId actual = userApiIdMapper.map(userApiId);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userApiId);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            userApiIdMapper.map(null);
        });
    }
}