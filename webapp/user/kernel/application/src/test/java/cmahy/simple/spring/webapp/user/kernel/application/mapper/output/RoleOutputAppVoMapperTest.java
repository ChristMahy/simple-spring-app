package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.*;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RightOutputAppVo;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RoleOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleOutputAppVoMapperTest {

    @Mock
    private RightOutputAppVoMapper rightOutputAppVoMapper;

    @InjectMocks
    private RoleOutputAppVoMapper roleOutputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            RoleStub role = new RoleStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString());

            role.setRights(
                Stream
                    .generate(() -> {
                        RightStub right = mock(RightStub.class);

                        when(rightOutputAppVoMapper.map(right)).thenAnswer(_ -> mock(RightOutputAppVo.class));

                        return right;
                    })
                    .limit(Generator.randomInt(10, 20))
                    .toList()
            );


            RoleOutputAppVo actual = roleOutputAppVoMapper.map(role);


            assertThat(actual).isNotNull();

            assertThat(actual.id()).isNotNull();
            assertThat(actual.id().value()).isEqualTo(role.getId());

            assertThat(actual.rights()).hasSize(role.getRights().size());

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
                .hasAllNullFieldsOrPropertiesExcept("rights");

            assertThat(actual.rights()).isEmpty();
        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {


            roleOutputAppVoMapper.map(null);


        });
    }
}