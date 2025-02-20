package cmahy.simple.spring.webapp.shell.client.impl.adapter.service;

import cmahy.simple.spring.webapp.shell.client.api.MainApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CommandLineFactoryTest {

    // https://picocli.info/#_mocking
    @Mock(withoutAnnotations = true)
    private MainApi mainApi;

    @Mock
    private CommandLine.IFactory factory;

    @InjectMocks
    private CommandLineFactory commandLineFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            CommandLine actual = commandLineFactory.create();

            assertThat(actual).isNotNull();

            MainApi actualCmd = actual.getCommand();

            assertThat(actualCmd).isEqualTo(mainApi);
            assertThat(actual.getFactory()).isEqualTo(factory);
        });
    }
}