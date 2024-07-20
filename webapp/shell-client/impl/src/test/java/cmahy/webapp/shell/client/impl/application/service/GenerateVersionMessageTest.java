package cmahy.webapp.shell.client.impl.application.service;

import cmahy.common.helper.Generator;
import cmahy.webapp.shell.client.impl.application.repository.property.BuildPropertyRepository;
import cmahy.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.webapp.shell.client.impl.application.vo.property.BuildPropertyVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateVersionMessageTest {

    @Mock
    private BuildPropertyRepository buildPropertyRepository;

    @Mock
    private ConsolePropertyRepository consolePropertyRepository;

    @Mock
    private DateTimeFormatter belgiumSimpleDateFormatter;

    @InjectMocks
    private GenerateVersionMessage generateVersionMessage;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            BuildPropertyVo buildPropertyVo = new BuildPropertyVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Instant.now(),
                Generator.generateAString(),
                Generator.generateAString()
            );
            String consoleFormatterPrefix = Generator.generateAStringWithoutSpecialChars(500);
            String consoleFormatterSuffix = Generator.generateAStringWithoutSpecialChars(500);

            String dateFormatted = Generator.generateAStringWithoutSpecialChars();

            when(buildPropertyRepository.buildProperty()).thenReturn(Optional.of(buildPropertyVo));
            when(consolePropertyRepository.findFormat()).thenReturn(Optional.of(consoleFormatterPrefix + "%s" + consoleFormatterSuffix));
            when(belgiumSimpleDateFormatter.format(buildPropertyVo.time())).thenReturn(dateFormatted);

            Optional<String> actual = generateVersionMessage.execute();

            String lineSeparator = System.lineSeparator();

            String expectedResult = consoleFormatterPrefix +
                lineSeparator + buildPropertyVo.group() + "." +
                buildPropertyVo.artifact() + lineSeparator.repeat(2) + "\t" + buildPropertyVo.version() + lineSeparator + "\t" +
                dateFormatted + lineSeparator + "\tJava " + buildPropertyVo.javaVersion() + lineSeparator.repeat(2) +
                buildPropertyVo.description() + lineSeparator.repeat(2) +
                consoleFormatterSuffix;

            assertThat(actual)
                .isNotEmpty()
                .hasValue(expectedResult);
        });
    }

    @Test
    void execute_onConsoleFormatNull_thenTextHasNotDecorations() {
        assertDoesNotThrow(() -> {
            BuildPropertyVo buildPropertyVo = new BuildPropertyVo(
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Generator.generateAString(),
                Instant.now(),
                Generator.generateAString(),
                Generator.generateAString()
            );

            String dateFormatted = Generator.generateAStringWithoutSpecialChars();

            when(buildPropertyRepository.buildProperty()).thenReturn(Optional.of(buildPropertyVo));
            when(consolePropertyRepository.findFormat()).thenReturn(Optional.empty());
            when(belgiumSimpleDateFormatter.format(buildPropertyVo.time())).thenReturn(dateFormatted);

            String lineSeparator = System.lineSeparator();

            String expectedResult = lineSeparator + buildPropertyVo.group() + "." +
                buildPropertyVo.artifact() + lineSeparator.repeat(2) + "\t" + buildPropertyVo.version() + lineSeparator + "\t" +
                dateFormatted + lineSeparator + "\tJava " + buildPropertyVo.javaVersion() + lineSeparator.repeat(2) +
                buildPropertyVo.description() + lineSeparator.repeat(2);

            Optional<String> actual = generateVersionMessage.execute();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(expectedResult);
        });
    }

    @Test
    void execute_onAnyNullPropertiesReplacedWithDefaultText() {
        assertDoesNotThrow(() -> {
            BuildPropertyVo buildPropertyVo = new BuildPropertyVo(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );
            String consoleFormatterPrefix = Generator.generateAStringWithoutSpecialChars(500);
            String consoleFormatterSuffix = Generator.generateAStringWithoutSpecialChars(500);

            when(buildPropertyRepository.buildProperty()).thenReturn(Optional.of(buildPropertyVo));
            when(consolePropertyRepository.findFormat()).thenReturn(Optional.of(consoleFormatterPrefix + "%s" + consoleFormatterSuffix));

            Optional<String> actual = generateVersionMessage.execute();

            String lineSeparator = System.lineSeparator();

            String expectedResult = consoleFormatterPrefix +
                lineSeparator + "NONE.NONE" +
                lineSeparator.repeat(2) + "\tNONE" + lineSeparator + "\tNONE" +
                lineSeparator + "\tJava NONE" + lineSeparator.repeat(2) +
                "NONE" + lineSeparator.repeat(2) +
                consoleFormatterSuffix;

            assertThat(actual)
                .isNotEmpty()
                .hasValue(expectedResult);
        });
    }

    @Test
    void execute_onEmptyBuildProperty_thenReturnEmptyString() {
        assertDoesNotThrow(() -> {
            when(buildPropertyRepository.buildProperty()).thenReturn(Optional.empty());

            Optional<String> actual = generateVersionMessage.execute();

            assertThat(actual).isEmpty();
        });
    }
}