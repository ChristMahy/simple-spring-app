package cmahy.webapp.resource.impl.application.user.mapper.input;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.user.vo.input.UserSecurityInputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.UserSecurity;
import cmahy.webapp.resource.impl.exception.NullException;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserSecurityInputAppVoMapperTest {

    @InjectMocks
    private UserSecurityInputAppVoMapper userSecurityInputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserSecurityInputAppVo userSecurityInputAppVo = new UserSecurityInputAppVo(
                Generator.generateAString(),
                Generator.generateAString().getBytes(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.randomEnum(AuthProvider.class),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                Generator.randomBoolean(),
                Generator.randomBoolean()
            );

            UserSecurity actual = userSecurityInputAppVoMapper.map(userSecurityInputAppVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("roles", "id")
                        .build()
                )
                .isEqualTo(userSecurityInputAppVo);
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            userSecurityInputAppVoMapper.map(null);
        });
    }
}