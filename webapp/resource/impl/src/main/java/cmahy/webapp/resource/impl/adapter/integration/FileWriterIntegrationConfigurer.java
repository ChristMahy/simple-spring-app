package cmahy.webapp.resource.impl.adapter.integration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;

import java.nio.file.Path;
import java.util.Optional;

import static cmahy.webapp.resource.impl.adapter.integration.ChannelName.TEXT_IN_CHANNEL;

@Configuration
public class FileWriterIntegrationConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(FileWriterIntegrationConfigurer.class);

    @Bean
    public IntegrationFlow fileWriterFlow(
        IntegrationFlowLogDirectoryLocationFactory directoryLocationFactory
    ) {
        return flow -> {
            try {
                flow
                    .channel(MessageChannels.direct(TEXT_IN_CHANNEL))
                    .<String, String>transform(StringUtils::upperCase)
                    .log(LoggingHandler.Level.INFO, "file.upper-case.category", Message::getPayload)
                    .handle(
                        Files
                            .outboundAdapter(directoryLocationFactory.get().toFile())
                            .fileExistsMode(FileExistsMode.APPEND)
                            .appendNewLine(true)
                    );
            } catch (Exception any) {
                LOG.error(any.getMessage(), any);
            }
        };
    }

    @Bean
    public IntegrationFlowLogDirectoryLocationFactory directoryLocationFactory(
        @Value("${application.integration-flow.directory:}")
        Optional<Path> directoryLocation,
        @Value("${spring.application.name:test-app-spring}")
        Optional<String> appName
    ) {
        return () -> {
            Path directoryLocationOrDefaultDirectoryLocation;

            if (directoryLocation.isPresent()) {
                if (!java.nio.file.Files.isDirectory(directoryLocation.get())) {
                    java.nio.file.Files.createDirectories(directoryLocation.get());
                }

                directoryLocationOrDefaultDirectoryLocation = directoryLocation.get();
            } else {
                directoryLocationOrDefaultDirectoryLocation = java.nio.file.Files.createTempDirectory(
                    appName.filter(StringUtils::isNotBlank).orElse("test-app-spring")
                );
            }

            LOG.info("Log directory location: {}", directoryLocationOrDefaultDirectoryLocation);

            return directoryLocationOrDefaultDirectoryLocation;
        };
    }
}
