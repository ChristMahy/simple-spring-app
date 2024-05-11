package cmahy.webapp.resource.impl.adapter.user.mapper.id;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.taco.shop.vo.input.TacoInputAppVo;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.id.UserApiId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserIdMapperTest {

    @InjectMocks
    private UserIdMapper userIdMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserId userId = new UserId(Generator.randomLongEqualOrAboveZero());

            UserApiId actual = userIdMapper.map(userId);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(userId);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            userIdMapper.map(null);
        });
    }
}