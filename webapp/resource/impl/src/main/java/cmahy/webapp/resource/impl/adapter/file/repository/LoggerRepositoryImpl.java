package cmahy.webapp.resource.impl.adapter.file.repository;

import cmahy.webapp.resource.impl.application.file.repository.LoggerRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static cmahy.webapp.resource.impl.adapter.integration.ChannelName.TEXT_IN_CHANNEL;

@MessagingGateway(defaultRequestChannel = TEXT_IN_CHANNEL)
public interface LoggerRepositoryImpl extends LoggerRepository {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    void appendLine(
        @Header(FileHeaders.FILENAME) String fileName,
        String line
    );

    @Override
    default void save(@NotNull String line) {
        appendLine("log-" + LocalDate.now().format(DATE_FORMATTER) + ".log", line);
    }
}
