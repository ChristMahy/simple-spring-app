package cmahy.webapp.resource.impl.adapter.api.stream.visitor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class StreamVisitorFactoryTest {

    @InjectMocks
    private StreamVisitorFactory streamVisitorFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            StreamVisitorImpl actual = streamVisitorFactory.create();

            assertThat(actual).isNotNull();
        });
    }
}