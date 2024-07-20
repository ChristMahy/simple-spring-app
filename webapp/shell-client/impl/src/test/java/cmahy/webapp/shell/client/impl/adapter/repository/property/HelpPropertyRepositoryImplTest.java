package cmahy.webapp.shell.client.impl.adapter.repository.property;

import cmahy.webapp.shell.client.impl.application.vo.property.help.HelpPropertyVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class HelpPropertyRepositoryImplTest {

    private HelpPropertyRepositoryImpl helpPropertyRepositoryImpl;

    @Test
    void helpProperty() {
        assertDoesNotThrow(() -> {
            HelpPropertyVo helpPropertyVo = mock(HelpPropertyVo.class);

            helpPropertyRepositoryImpl = new HelpPropertyRepositoryImpl(helpPropertyVo);

            Optional<HelpPropertyVo> actual = helpPropertyRepositoryImpl.helpProperty();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(helpPropertyVo);
        });
    }

    @Test
    void helpProperty_whenGivenValueIsNull_thenReturnEmpty() {
        assertDoesNotThrow(() -> {
            helpPropertyRepositoryImpl = new HelpPropertyRepositoryImpl(null);

            Optional<HelpPropertyVo> actual = helpPropertyRepositoryImpl.helpProperty();

            assertThat(actual).isEmpty();
        });
    }
}