package cmahy.webapp.resource.impl.application.stream.query.singlefile;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.application.service.StringGeneratorService;
import cmahy.webapp.resource.impl.application.stream.visitor.StreamVisitor;
import cmahy.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import com.jayway.jsonpath.internal.filter.PatternFlag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.Random;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateReadmeWithRandomContentQueryTest {

    @Mock
    private StringGeneratorService stringGenerator;

    @Mock
    private Random random;

    @InjectMocks
    private GenerateReadmeWithRandomContentQuery query;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            try (var outputStream = new ByteArrayOutputStream()) {
                StreamVisitor<String, StreamConsumer, StreamConsumer> visitor = new StreamVisitor<String, StreamConsumer, StreamConsumer>() {
                    @Override
                    public StreamConsumer build(StreamConsumer body) {
                        return body;
                    }
                };

                var stringSize = 5 * 1024;
                var expectedRandomString = Generator.generateAString(stringSize);
                when(stringGenerator.execute(stringSize)).thenReturn(expectedRandomString);

                StreamConsumer actual = query.execute(visitor, Boolean.FALSE);

                actual.writeTo(outputStream);

                var pattern = Pattern.compile("^Line <\\d+>, " + Pattern.quote(expectedRandomString) + ".*$", Pattern.MULTILINE);
                var matcher = pattern.matcher(outputStream.toString());

                var cpt = 0;

                while (matcher.find()) {
                    cpt++;
                }

                assertThat(cpt).isEqualTo(1024);
            }
        });
    }

    @Test
    void execute_whenOnFailureIsTrue_thenShouldNotFireIOExceptionUntilTheResultOfBuildIsExecuted() {
        assertDoesNotThrow(() -> {
            StreamVisitor<String, StreamConsumer, StreamConsumer> visitor = new StreamVisitor<String, StreamConsumer, StreamConsumer>() {
                @Override
                public StreamConsumer build(StreamConsumer body) {
                    return body;
                }
            };

            var stringSize = 5 * 1024;
            var expectedRandomString = Generator.generateAString(stringSize);
            when(stringGenerator.execute(stringSize)).thenReturn(expectedRandomString);

            when(random.nextInt(0, 1024 - 1)).thenReturn(Generator.randomInt(0, 1023));

            StreamConsumer actual = query.execute(visitor, Boolean.TRUE);

            assertThrows(IOException.class, () -> {
                try (var outputStream = new ByteArrayOutputStream()) {
                    actual.writeTo(outputStream);
                }
            });
        });
    }
}