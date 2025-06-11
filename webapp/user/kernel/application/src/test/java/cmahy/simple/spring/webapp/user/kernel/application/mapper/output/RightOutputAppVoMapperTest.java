package cmahy.simple.spring.webapp.user.kernel.application.mapper.output;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.RightStub;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import cmahy.simple.spring.webapp.user.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.user.kernel.vo.output.RightOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RightOutputAppVoMapperTest {

    @InjectMocks
    private RightOutputAppVoMapper rightOutputAppVoMapper;

    @Test
    void map() {
        assertDoesNotThrow(() -> {
            Right right = new RightStub()
                .setId(Generator.randomUUID())
                .setName(Generator.generateAString());


            RightOutputAppVo actual = rightOutputAppVoMapper.map(right);


            assertThat(actual).isNotNull();

            assertThat(actual.id())
                .isNotNull()
                .extracting(RightId::value)
                .isEqualTo(right.getId());

            assertThat(actual.name()).isEqualTo(right.getName());
        });
    }

    @Test
    void map_whenAllPropertiesAreNull_thenResultPropertiesAreNull() {
        assertDoesNotThrow(() -> {

            Right right = new RightStub();


            RightOutputAppVo actual = rightOutputAppVoMapper.map(right);


            assertThat(actual)
                .isNotNull()
                .hasAllNullFieldsOrProperties();

        });
    }

    @Test
    void map_whenSourceIsNull_thenThrowNullException() {
        assertThrows(RequiredException.class, () -> {


            rightOutputAppVoMapper.map(null);


        });
    }
}