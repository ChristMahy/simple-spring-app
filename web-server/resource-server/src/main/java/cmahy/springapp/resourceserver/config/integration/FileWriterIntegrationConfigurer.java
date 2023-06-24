package cmahy.springapp.resourceserver.config.integration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.IOException;

@Configuration
public class FileWriterIntegrationConfigurer {

    @Bean
    public IntegrationFlow fileWriterFlow(@Value("spring.application.name") String appName) {
        return (flow) -> {
            try {
                flow
                    .channel(MessageChannels.direct("textInChannel"))
                    .<String, String>transform(String::toUpperCase)
                    .handle(
                        Files
                            .outboundAdapter(
                                java.nio.file.Files.createTempDirectory(StringUtils.isBlank(appName) ? "spring-app-test" : appName).toFile()
                            )
                            .fileExistsMode(FileExistsMode.APPEND)
                            .appendNewLine(true)
                    );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
