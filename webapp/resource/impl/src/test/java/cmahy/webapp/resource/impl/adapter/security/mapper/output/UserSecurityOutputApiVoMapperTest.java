package cmahy.webapp.resource.impl.adapter.security.mapper.output;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.user.mapper.id.UserIdMapper;
import cmahy.webapp.resource.impl.adapter.user.mapper.output.RoleOutputApiVoMapper;
import cmahy.webapp.resource.impl.application.user.vo.output.RoleOutputAppVo;
import cmahy.webapp.resource.impl.application.user.vo.output.UserSecurityOutputAppVo;
import cmahy.webapp.resource.impl.domain.user.AuthProvider;
import cmahy.webapp.resource.impl.domain.user.id.UserId;
import cmahy.webapp.resource.impl.exception.NullException;
import cmahy.webapp.resource.user.id.UserApiId;
import cmahy.webapp.resource.user.vo.output.RoleOutputApiVo;
import cmahy.webapp.resource.user.vo.output.UserSecurityOutputApiVo;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSecurityOutputApiVoMapperTest {

    @Mock
    private UserIdMapper userIdMapper;

    @Mock
    private RoleOutputApiVoMapper roleOutputApiVoMapper;

    @InjectMocks
    private UserSecurityOutputApiVoMapper userSecurityOutputApiVoMapper;


    @Test
    void map() {
        assertDoesNotThrow(() -> {
            UserSecurityOutputAppVo securityOutputAppVo = new UserSecurityOutputAppVo(
                mock(UserId.class),
                Generator.generateAString(),
                Generator.randomBytes(50),
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
                Generator.randomBoolean(),
                Stream.generate(() -> {
                    RoleOutputAppVo roleOutput = mock(RoleOutputAppVo.class);
                    RoleOutputApiVo roleOutputApi = mock(RoleOutputApiVo.class);

                    when(roleOutputApiVoMapper.map(roleOutput)).thenReturn(roleOutputApi);

                    return roleOutput;
                })
                    .limit(Generator.randomInt(10, 50))
                    .collect(Collectors.toSet())
            );
            UserApiId apiId = mock(UserApiId.class);

            when(userIdMapper.map(securityOutputAppVo.id())).thenReturn(apiId);

            UserSecurityOutputApiVo actual = userSecurityOutputApiVoMapper.map(securityOutputAppVo);

            assertThat(actual)
                .isNotNull()
                .usingRecursiveComparison(
                    RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("roles", "id", "authProvider")
                        .build()
                )
                .isEqualTo(securityOutputAppVo);

            assertThat(actual.id()).isEqualTo(apiId);
            assertThat(actual.roles()).hasSize(securityOutputAppVo.roles().size());
            assertThat(actual.authProvider()).isEqualTo(securityOutputAppVo.authProvider().name());
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(NullException.class, () -> {
            userSecurityOutputApiVoMapper.map(null);
        });
    }
}