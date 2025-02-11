package cmahy.webapp.resource.impl.application.stream.service.zip;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.service.StringGeneratorService;
import cmahy.webapp.resource.impl.application.stream.exception.FailAtPositionException;
import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZipRandomContentWriterTasksFactoryTest {

    @Mock
    private StringGeneratorService stringGeneratorService;

    @Mock
    private Random randomizer;

    @InjectMocks
    private ZipRandomContentWriterTasksFactory zipRandomContentWriterTasksFactory;

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

            var zipEntryProxy = mock(ZipEntryProxy.class);

            var actual = zipRandomContentWriterTasksFactory.execute(options);

            assertThat(actual)
                .isNotNull()
                .hasSize(elementsSize);

            for (var task : actual) {
                task.execute(zipEntryProxy);
            }

            verify(stringGeneratorService, times(elementsSize)).execute(stringSize);

            verify(zipEntryProxy, times(elementsSize)).appendToEntryContent(any(byte[].class));
        });
    }

    @Test
    void execute_whenFailureAsked_thenShouldContinueAndStopProcessAtDedicatedPositionThenThrowAnException() {
        assertDoesNotThrow(() -> {
            var elementsSize = Generator.randomInt(100, 2000);
            var stringSize = Generator.randomInt(5, 1000);

            var failAtPosition = Generator.randomInt(0, elementsSize - 1);

            var options = new GeneratorQueryOption(
                Boolean.TRUE,
                Optional.of(stringSize),
                Optional.of(elementsSize)
            );

            var zipEntryProxy = mock(ZipEntryProxy.class);

            when(randomizer.nextInt(0, elementsSize)).thenReturn(failAtPosition);

            var actual = zipRandomContentWriterTasksFactory.execute(options);

            assertThat(actual)
                .isNotNull()
                .hasSize(elementsSize);

            assertThrows(FailAtPositionException.class, () -> {
                for (var task : actual) {
                    task.execute(zipEntryProxy);
                }
            });

            verify(stringGeneratorService, times(failAtPosition)).execute(stringSize);

            verify(zipEntryProxy, times(failAtPosition)).appendToEntryContent(any(byte[].class));
        });
    }
}