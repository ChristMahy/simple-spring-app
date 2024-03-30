package cmahy.common.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JsonMapperFactoryTest {

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            assertThat(JsonMapperFactory.create()).isNotNull();
        });
    }
}