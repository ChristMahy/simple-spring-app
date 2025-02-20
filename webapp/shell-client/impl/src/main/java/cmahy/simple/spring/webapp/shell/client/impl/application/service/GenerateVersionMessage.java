package cmahy.simple.spring.webapp.shell.client.impl.application.service;

import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.BuildPropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Named
public class GenerateVersionMessage {

    private final BuildPropertyRepository buildPropertyRepository;
    private final ConsolePropertyRepository consolePropertyRepository;
    private final DateTimeFormatter belgiumSimpleDateFormatter;

    public GenerateVersionMessage(
        BuildPropertyRepository buildPropertyRepository,
        ConsolePropertyRepository consolePropertyRepository,
        DateTimeFormatter belgiumSimpleDateFormatter
    ) {
        this.buildPropertyRepository = buildPropertyRepository;
        this.consolePropertyRepository = consolePropertyRepository;
        this.belgiumSimpleDateFormatter = belgiumSimpleDateFormatter;
    }

    public Optional<String> execute() {
        String lineSeparator = System.lineSeparator();

        return buildPropertyRepository.buildProperty()
            .map(buildPropertyVo -> String.format(
                consolePropertyRepository
                    .findFormat()
                    .orElse("%s"),
                String.format(
                    lineSeparator + "%s.%s" + lineSeparator.repeat(2) + "\t%s" +
                        lineSeparator + "\t%s" + lineSeparator + "\tJava %s" +
                        lineSeparator.repeat(2) + "%s" + lineSeparator.repeat(2),
                    StringUtils.defaultIfBlank(buildPropertyVo.group(), "NONE"),
                    StringUtils.defaultIfBlank(buildPropertyVo.artifact(), "NONE"),
                    StringUtils.defaultIfBlank(buildPropertyVo.version(), "NONE"),
                    Optional.ofNullable(buildPropertyVo.time())
                        .map(belgiumSimpleDateFormatter::format)
                        .orElse("NONE"),
                    StringUtils.defaultIfBlank(buildPropertyVo.javaVersion(), "NONE"),
                    StringUtils.defaultIfBlank(buildPropertyVo.description(), "NONE")
                )
            ));
    }
}
