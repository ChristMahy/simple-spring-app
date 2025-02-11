package cmahy.webapp.resource.impl.application.stream.query.zip;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.stream.service.singlefile.executor.SingleFileReadmeTaskExecutor;
import cmahy.webapp.resource.impl.application.stream.service.zip.*;
import cmahy.webapp.resource.impl.application.stream.service.zip.executor.ZipSingleEntryTaskExecutor;
import cmahy.webapp.resource.impl.application.stream.service.zip.factory.ZipEntryProxyFactory;
import cmahy.webapp.resource.impl.application.stream.service.zip.factory.ZipProxyFactory;
import cmahy.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import cmahy.webapp.resource.impl.helper.stream.visitor.StreamVisitorTestImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateZipWithRandomContentQueryTest {

    private DateTimeFormatter yearMonthDayHoursMinutesSeconds;

    @Mock
    private ZipProxyFactory zipProxyFactory;

    @Mock
    private ZipEntryProxyFactory zipEntryProxyFactory;

    @Mock
    private ZipRandomContentWriterTasksFactory tasksFactory;

    private GenerateZipWithRandomContentQuery generateZipWithRandomContentQuery;

    @BeforeEach
    void setUp() {
        yearMonthDayHoursMinutesSeconds = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        generateZipWithRandomContentQuery = new GenerateZipWithRandomContentQuery(
            yearMonthDayHoursMinutesSeconds,
            tasksFactory,
            zipProxyFactory,
            zipEntryProxyFactory
        );
    }

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

            var task = mock(ZipSingleEntryTaskExecutor.class);

            var tasks = IntStream.rangeClosed(1, elementsSize)
                .mapToObj(index -> task)
                .toList();

            when(tasksFactory.execute(options)).thenReturn(tasks);

            var actual = generateZipWithRandomContentQuery.execute(visitor, options);

            assertThat(actual).isNotNull();

            var outputStream = mock(OutputStream.class);
            var zipProxy = mock(ZipProxy.class);
            var zipEntryProxy = mock(ZipEntryProxy.class);

            when(zipProxyFactory.create(outputStream)).thenReturn(zipProxy);
            when(zipEntryProxyFactory.create(zipProxy)).thenReturn(zipEntryProxy);

            actual.execute(outputStream);

            assertThat(visitor.getPrepareResult()).isNotEmpty();
            assertThat(visitor.getPrepareResult().get()).matches("^\\S+-[0-9]{14}.zip$");

            verify(task, times(tasks.size())).execute(zipEntryProxy);
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
            var zipProxy = mock(ZipProxy.class);
            var zipEntryProxy = mock(ZipEntryProxy.class);
            var expectedException = new IOException(Generator.generateAString());

            var task = mock(ZipSingleEntryTaskExecutor.class);

            var tasks = IntStream.rangeClosed(1, elementsSize)
                .mapToObj(index -> {
                    if (index == failAtPosition) {
                        var failureTask = mock(ZipSingleEntryTaskExecutor.class);

                        try {
                            doAnswer(invocationOnMock -> {
                                throw expectedException;
                            })
                                .when(failureTask)
                                .execute(zipEntryProxy);
                        } catch (IOException ignored) {
                        }

                        return failureTask;
                    }

                    return task;
                })
                .toList();

            var actualException = assertThrows(IOException.class, () -> {
                when(tasksFactory.execute(options)).thenReturn(tasks);

                var actual = generateZipWithRandomContentQuery.execute(visitor, options);

                assertThat(actual).isNotNull();

                when(zipProxyFactory.create(outputStream)).thenReturn(zipProxy);
                when(zipEntryProxyFactory.create(zipProxy)).thenReturn(zipEntryProxy);

                actual.execute(outputStream);
            });

            assertThat(actualException).isEqualTo(expectedException);

            verify(task, times(failAtPosition - 1)).execute(zipEntryProxy);
        });
    }
}