package cmahy.plugins.architecture.domain.validator;

import cmahy.plugins.architecture.configuration.ProjectConfiguration;
import cmahy.plugins.architecture.validator.ArchitectureValidator;
import cmahy.plugins.architecture.rule.MainCodingRule;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;
import java.nio.file.Paths;

import static cmahy.plugins.architecture.rule.SpringArchRule.NO_SPRING_BOOT_DEPENDENCY;
import static cmahy.plugins.architecture.rule.MainCodingRule.*;

public class DomainArchitectureValidatorImpl implements ArchitectureValidator {

    private final ProjectConfiguration projectConfiguration;
    private final Log logger;

    public DomainArchitectureValidatorImpl(ProjectConfiguration projectConfiguration, Log logger) {
        this.projectConfiguration = projectConfiguration;
        this.logger = logger;
    }

    @Override
    public void validate() {

        Path targetClasses = Paths.get(projectConfiguration.build().directory(), "classes");

        JavaClasses importedClasses = new ClassFileImporter(MainCodingRule.IMPORTS_OPTIONS)
            .importPath(targetClasses);

        logger.info("Imported classes <" + importedClasses.size() + ">");

        logger.debug("Checking no spring boot dependencies");

        NO_SPRING_BOOT_DEPENDENCY.check(importedClasses);

        AVOID_IO_STANDARD_STREAM_USE.check(importedClasses);

        AVOID_JAVA_UTIL_LOGGING_USE.check(importedClasses);

        LOGGER_AS_PRIVATE_STATIC_FINAL.check(importedClasses);

        AVOID_JODA_TIME_USE.check(importedClasses);

        logger.info( "Domain is valid");

    }

}
