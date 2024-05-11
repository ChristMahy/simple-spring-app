package cmahy.webapp.resource.impl.adapter.user.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.user.mapper.id.RoleIdMapper;
import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.id.RoleId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.api.vo.id.RoleApiId;
import cmahy.webapp.resource.user.api.vo.output.RoleOutputApiVo;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleOutputApiVoMapperTest {

    @Mock
    private RoleIdMapper roleIdMapper;

    @InjectMocks
    private RoleOutputApiVoMapper roleOutputApiVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            RoleOutputAppVo output = new RoleOutputAppVo(
                mock(RoleId.class),
                Generator.generateAString()
            );
            RoleApiId roleApiId = mock(RoleApiId.class);

            when(roleIdMapper.map(output.id())).thenReturn(roleApiId);

            RoleOutputApiVo actual = roleOutputApiVoMapper.map(output);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("id")
                        .build()
                )
                .isEqualTo(output);

            assertThat(actual.id()).isEqualTo(roleApiId);
        });
    }

    @Test
    void map_whenGivenValueIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            roleOutputApiVoMapper.map(null);
        });
    }

    @Test
    void map_whenGivenValueForIdIsNull_thenThrowNullAssertion() {
        assertThrows(NullException.class, () -> {
            when(roleIdMapper.map(null)).thenThrow(NullException.class);

            roleOutputApiVoMapper.map(new RoleOutputAppVo(null, Generator.generateAString()));
        });
    }
}