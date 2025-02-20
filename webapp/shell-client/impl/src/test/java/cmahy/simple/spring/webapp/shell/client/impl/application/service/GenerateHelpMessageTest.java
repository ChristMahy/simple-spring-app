package cmahy.simple.spring.webapp.shell.client.impl.application.service;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.HelpPropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.help.GeneralPropertyVo;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.help.HelpPropertyVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateHelpMessageTest {

    @Mock
    private ConsolePropertyRepository consolePropertyRepository;

    @Mock
    private HelpPropertyRepository helpPropertyRepository;

    @InjectMocks
    private GenerateHelpMessage generateHelpMessage;

    @Test
    void execute() {
        assertDoesNotThrow(() -> {
            String consoleFormatPrefix = Generator.generateAStringWithoutSpecialChars(500);
            String consoleFormatSuffix = Generator.generateAStringWithoutSpecialChars(500);

            HelpPropertyVo helpPropertyVo = new HelpPropertyVo(
                new GeneralPropertyVo(
                    Generator.generateAString(1000)
                )
            );

            when(helpPropertyRepository.helpProperty()).thenReturn(Optional.of(helpPropertyVo));
            when(consolePropertyRepository.findFormat()).thenReturn(Optional.of(consoleFormatPrefix + "%s" + consoleFormatSuffix));

            Optional<String> actual = generateHelpMessage.execute();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(consoleFormatPrefix + helpPropertyVo.general().message() + consoleFormatSuffix);
        });
    }

    @Test
    void execute_onConsoleEmpty_thenUseDefaultFormatter() {
        assertDoesNotThrow(() -> {
            HelpPropertyVo helpPropertyVo = new HelpPropertyVo(
                new GeneralPropertyVo(
                    Generator.generateAString(1000)
                )
            );

            when(helpPropertyRepository.helpProperty()).thenReturn(Optional.of(helpPropertyVo));
            when(consolePropertyRepository.findFormat()).thenReturn(Optional.empty());

            Optional<String> actual = generateHelpMessage.execute();

            assertThat(actual)
                .isNotEmpty()
                .hasValue(helpPropertyVo.general().message());
        });
    }

    @ParameterizedTest
    @MethodSource("emptyValues")
    void execute_onHelpPropertyHasNullValue(HelpPropertyVo helpPropertyVo) {
        assertDoesNotThrow(() -> {
            when(helpPropertyRepository.helpProperty()).thenReturn(Optional.ofNullable(helpPropertyVo));

            Optional<String> actual = generateHelpMessage.execute();

            assertThat(actual).isEmpty();
        });
    }

    protected static Stream<HelpPropertyVo> emptyValues() {
        return Stream.of(
            new HelpPropertyVo(new GeneralPropertyVo(null)),
            new HelpPropertyVo(null),
            null
        );
    }
}