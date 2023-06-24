package cmahy.springapp.resourceserver.config.integration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.IOException;

@Configuration
public class FileWriterIntegrationConfigurer {

    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return String::toUpperCase;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter(@Value("spring.application.name") String appName) throws IOException {
        File file = java.nio.file.Files.createTempDirectory(StringUtils.isBlank(appName) ? "spring-app-test" : appName).toFile();

        FileWritingMessageHandler handler = new FileWritingMessageHandler(file);

        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }

    // Non obligatoire, car construit par défaut
    @Bean
    public MessageChannel textInChannel() {
        return new DirectChannel();
    }

    // Non obligatoire, car construit par défaut
    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }
}
