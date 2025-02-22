package cmahy.simple.spring.webapp.resource.impl.application.stream.service.singlefile;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.application.service.StringGeneratorService;
import cmahy.simple.spring.webapp.resource.impl.application.stream.exception.FailAtPositionException;
import cmahy.simple.spring.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.OutputStream;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SingleReadmeRandomContentWriterTaskFactoryTest {

    @Mock
    private StringGeneratorService stringGeneratorService;

    @Mock
    private Random randomizer;

    @InjectMocks
    private SingleReadmeRandomContentWriterTaskFactory singleReadmeRandomContentWriterTaskFactory;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var elementsSize = Generator.randomInt(100, 2000);
            var stringSize = Generator.randomInt(5, 1000);

            var options = new GeneratorQueryOption(
                Boolean.FALSE,
                Optional.of(stringSize),
                Optional.of(elementsSize)
            );

            var outputStream = mock(OutputStream.class);

            var actual = singleReadmeRandomContentWriterTaskFactory.execute(options);

            assertThat(actual)
                .isNotNull()
                .hasSize(elementsSize);

            for (var task : actual) {
                task.execute(outputStream);
            }

            verify(stringGeneratorService, times(elementsSize)).execute(stringSize);

            verify(outputStream, times(elementsSize)).write(any(byte[].class));
            verify(outputStream, times(elementsSize)).flush();
        });
    }

    @Test
    void execute_whenFailureAsked_thenShouldContinueAndStopProcessAtDedicatedPositionThenThrowAnException() {
        assertDoesNotThrow(() -> {
            var elementsSize = Generator.randomInt(100, 2000);
            var stringSize = Generator.randomInt(5, 1000);

            var failAtPosition = Generator.randomInt(1, elementsSize);

            var options = new GeneratorQueryOption(
                Boolean.TRUE,
                Optional.of(stringSize),
                Optional.of(elementsSize)
            );

            var outputStream = mock(OutputStream.class);

            when(randomizer.nextInt(1, elementsSize)).thenReturn(failAtPosition);

            var actual = singleReadmeRandomContentWriterTaskFactory.execute(options);

            assertThat(actual)
                .isNotNull()
                .hasSize(elementsSize);

            assertThrows(FailAtPositionException.class, () -> {
                for (var task : actual) {
                    task.execute(outputStream);
                }
            });

            verify(stringGeneratorService, times(failAtPosition)).execute(stringSize);

            verify(outputStream, times(failAtPosition)).write(any(byte[].class));
            verify(outputStream, times(failAtPosition)).flush();
        });
    }
}