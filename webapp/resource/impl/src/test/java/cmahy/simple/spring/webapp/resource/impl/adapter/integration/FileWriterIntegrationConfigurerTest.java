package cmahy.simple.spring.webapp.resource.impl.adapter.integration;

import cmahy.simple.spring.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.dsl.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileWriterIntegrationConfigurerTest {

    @InjectMocks
    private FileWriterIntegrationConfigurer fileWriterIntegrationConfigurer;

    @Test
    void fileWriterFlow() {
        assertDoesNotThrow(() -> {
            Path directoryLocation = Path.of(Generator.generateAStringWithoutSpecialChars());
            IntegrationFlowLogDirectoryLocationFactory directoryLocationFactory = mock(IntegrationFlowLogDirectoryLocationFactory.class);
            when(directoryLocationFactory.get()).thenReturn(directoryLocation);

            IntegrationFlow flow = fileWriterIntegrationConfigurer.fileWriterFlow(directoryLocationFactory);

            IntegrationFlowDefinition<?> flowDefinition = mock(IntegrationFlowDefinition.class, RETURNS_SELF);

            flow.configure(flowDefinition);
        });
    }

    @ParameterizedTest
    @MethodSource("aBunchOfExceptions")
    void fileWriterFlow_onIOException_thenLogErrorAndKeepThreadSafe(Throwable throwable) {
        assertDoesNotThrow(() -> {
            IntegrationFlowLogDirectoryLocationFactory directoryLocationFactory = mock(IntegrationFlowLogDirectoryLocationFactory.class);

            IntegrationFlow flow = fileWriterIntegrationConfigurer.fileWriterFlow(directoryLocationFactory);

            IntegrationFlowDefinition<?> flowDefinition = mock(IntegrationFlowDefinition.class, RETURNS_SELF);

            when(flowDefinition.channel(any(DirectChannelSpec.class))).thenAnswer(_ -> {
                throw throwable;
            });

            flow.configure(flowDefinition);
        });
    }

    private static Stream<Throwable> aBunchOfExceptions() {
        return Stream.of(
            new IOException(Generator.generateAString()),
            new RuntimeException(Generator.generateAString()),
            new Exception(Generator.generateAString())
        );
    }

    @Test
    void directoryLocationFactory() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<java.nio.file.Files> filesMocked = mockStatic(java.nio.file.Files.class)) {
                Path directory = Path.of(Generator.generateAStringWithoutSpecialChars());
                String appName = Generator.generateAStringWithoutSpecialChars();

                filesMocked.when(() -> java.nio.file.Files.isDirectory(directory)).thenReturn(true);

                IntegrationFlowLogDirectoryLocationFactory directoryLocation = fileWriterIntegrationConfigurer.directoryLocationFactory(
                    Optional.of(directory),
                    Optional.of(appName)
                );

                Path actual = directoryLocation.get();

                assertThat(actual)
                    .isNotNull()
                    .isEqualTo(directory);

                filesMocked.verify(() -> java.nio.file.Files.isDirectory(directory));
                filesMocked.verifyNoMoreInteractions();
            }
        });
    }

    @Test
    void directoryLocationFactory_whenDirectoryDoesNotExist_thenCreateIt() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<java.nio.file.Files> filesMocked = mockStatic(java.nio.file.Files.class)) {
                Path directory = Path.of(Generator.generateAStringWithoutSpecialChars());
                String appName = Generator.generateAStringWithoutSpecialChars();

                filesMocked.when(() -> java.nio.file.Files.isDirectory(directory)).thenReturn(false);

                IntegrationFlowLogDirectoryLocationFactory directoryLocation = fileWriterIntegrationConfigurer.directoryLocationFactory(
                    Optional.of(directory),
                    Optional.of(appName)
                );

                Path actual = directoryLocation.get();

                assertThat(actual)
                    .isNotNull()
                    .isEqualTo(directory);

                filesMocked.verify(() -> java.nio.file.Files.isDirectory(directory));
                filesMocked.verify(() -> java.nio.file.Files.createDirectories(directory));
                filesMocked.verifyNoMoreInteractions();
            }
        });
    }

    @Test
    void directoryLocationFactory_whenDirectoryPropertyIsEmpty_thenUseTempDirectory() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<java.nio.file.Files> filesMocked = mockStatic(java.nio.file.Files.class)) {
                String appName = Generator.generateAStringWithoutSpecialChars();
                Path tempDirectory = mock(Path.class);

                filesMocked.when(() -> java.nio.file.Files.createTempDirectory(appName)).thenReturn(tempDirectory);

                IntegrationFlowLogDirectoryLocationFactory directoryLocation = fileWriterIntegrationConfigurer.directoryLocationFactory(
                    Optional.empty(),
                    Optional.of(appName)
                );

                Path actual = directoryLocation.get();

                assertThat(actual)
                    .isNotNull()
                    .isEqualTo(tempDirectory);

                filesMocked.verify(() -> java.nio.file.Files.createTempDirectory(appName));
                filesMocked.verifyNoMoreInteractions();
            }
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"\t", "         "})
    void directoryLocationFactory_whenDirectoryPropertyAndAppNameAreEmpty_thenUseTempDirectoryAndADefaultAppName(String nullOrEmptyValue) {
        assertDoesNotThrow(() -> {
            try (MockedStatic<java.nio.file.Files> filesMocked = mockStatic(java.nio.file.Files.class)) {
                Path tempDirectory = mock(Path.class);

                filesMocked.when(() -> java.nio.file.Files.createTempDirectory("test-app-spring")).thenReturn(tempDirectory);

                IntegrationFlowLogDirectoryLocationFactory directoryLocation = fileWriterIntegrationConfigurer.directoryLocationFactory(
                    Optional.empty(),
                    Optional.ofNullable(nullOrEmptyValue)
                );

                Path actual = directoryLocation.get();

                assertThat(actual)
                    .isNotNull()
                    .isEqualTo(tempDirectory);

                filesMocked.verify(() -> java.nio.file.Files.createTempDirectory("test-app-spring"));
                filesMocked.verifyNoMoreInteractions();
            }
        });
    }
}