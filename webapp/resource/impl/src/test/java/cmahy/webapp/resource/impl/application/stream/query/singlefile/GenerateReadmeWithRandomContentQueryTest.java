package cmahy.webapp.resource.impl.application.stream.query.singlefile;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.stream.service.singlefile.SingleReadmeRandomContentWriterTaskFactory;
import cmahy.webapp.resource.impl.application.stream.service.singlefile.executor.SingleFileReadmeTaskExecutor;
import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import cmahy.webapp.resource.impl.helper.stream.visitor.StreamVisitorTestImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateReadmeWithRandomContentQueryTest {

    @Mock
    private SingleReadmeRandomContentWriterTaskFactory taskGenerator;

    @InjectMocks
    private GenerateReadmeWithRandomContentQuery query;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            var elementsSize = Generator.randomInt(100, 2000);

            var visitor = new StreamVisitorTestImpl();
            var options = new GeneratorQueryOption(
                Boolean.FALSE,
                Optional.of(5),
                Optional.of(elementsSize)
            );

            var task = mock(SingleFileReadmeTaskExecutor.class);

            var tasks = IntStream.rangeClosed(1, elementsSize)
                .mapToObj(index -> task)
                .toList();

            when(taskGenerator.execute(options)).thenReturn(tasks);

            var actual = query.execute(visitor, options);

            assertThat(actual).isNotNull();

            var outputStream = mock(OutputStream.class);

            actual.execute(outputStream);

            assertThat(visitor.getPrepareResult()).isNotEmpty();
            assertThat(visitor.getPrepareResult().get()).matches("^\\S+.md$");

            verify(task, times(tasks.size())).execute(outputStream);
        });
    }

    @Test
    void execute_whenFailureAsked_thenShouldNotFireIOExceptionUntilTheResultOfBuildIsExecutedAndShouldKeepWriteToOutputStream() {
        assertDoesNotThrow(() -> {
            var elementsSize = Generator.randomInt(100, 2000);
            var failAtPosition = Generator.randomInt(0, elementsSize);

            var visitor = new StreamVisitorTestImpl();
            var options = new GeneratorQueryOption(
                Boolean.TRUE,
                Optional.of(5),
                Optional.of(elementsSize)
            );

            var outputStream = mock(OutputStream.class);
            var expectedException = new IOException(Generator.generateAString());

            var task = mock(SingleFileReadmeTaskExecutor.class);

            var tasks = IntStream.rangeClosed(1, elementsSize)
                .mapToObj(index -> {
                    if (index == failAtPosition) {
                        var failureTask = mock(SingleFileReadmeTaskExecutor.class);

                        try {
                            doAnswer(invocationOnMock -> {
                                throw expectedException;
                            })
                                .when(failureTask)
                                .execute(outputStream);
                        } catch (IOException ignored) {}

                        return failureTask;
                    }

                    return task;
                })
                .toList();

            var actualException = assertThrows(IOException.class, () -> {
                when(taskGenerator.execute(options)).thenReturn(tasks);

                var actual = query.execute(visitor, options);

                assertThat(actual).isNotNull();

                actual.execute(outputStream);
            });

            assertThat(actualException).isEqualTo(expectedException);

            verify(task, times(failAtPosition - 1)).execute(outputStream);
        });
    }
}