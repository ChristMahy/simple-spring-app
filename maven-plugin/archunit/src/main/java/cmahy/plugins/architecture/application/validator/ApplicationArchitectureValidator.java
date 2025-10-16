package cmahy.plugins.architecture.application.validator;

import cmahy.plugins.architecture.application.configuration.*;
import cmahy.plugins.architecture.configuration.*;
import cmahy.plugins.architecture.rule.MainCodingRule;
import cmahy.plugins.architecture.validator.*;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;
import java.nio.file.Paths;

import static cmahy.plugins.architecture.application.rule.ApplicationArchRule.*;
import static cmahy.plugins.architecture.application.rule.CommandArchRule.*;
import static cmahy.plugins.architecture.application.rule.QueryArchRule.*;

public class ApplicationArchitectureValidator implements ArchitectureValidator {

    private final ProjectConfiguration projectConfiguration;
    private final ApplicationValidatorConfiguration configuration;
    private final Log logger;

    public ApplicationArchitectureValidator(
        ProjectConfiguration projectConfiguration, ValidatorConfiguration validatorConfiguration, Log logger
    ) {

        this.projectConfiguration = projectConfiguration;

        if (validatorConfiguration instanceof ApplicationValidatorConfiguration config) {
            this.configuration = config;
        } else {
            throw new IllegalStateException("Not yet implemented !");
//            this.configuration = new ApplicationValidatorConfiguration() {
//                @Override
//                public CommandConfiguration command() {
//
//                }
//
//                @Override
//                public QueryConfiguration query() {
//                    throw new IllegalStateException("Not yet implemented !");
//                }
//            };
        }

        this.logger = logger;
    }

    @Override
    public void validate() {

        Path targetClasses = Paths.get(projectConfiguration.build().directory(), "classes");

        JavaClasses importedClasses = new ClassFileImporter(MainCodingRule.IMPORTS_OPTIONS)
            .importPath(targetClasses);

        logger.info("Imported classes <" + importedClasses.size() + ">");

        if (configuration.activePackages().contains(ApplicationLayer.application)) {
            APPLICATION_LAYER_HAS_TO_RESPECT_HEXAGONAL_ARCHITECTURE.check(importedClasses);

            NO_SPRING_DEPENDENCY_INSIDE_APPLICATION.check(importedClasses);

            NO_SPRING_INJECTION_INSIDE_APPLICATION.check(importedClasses);

            NO_SPRING_BEAN_DECLARATION_FROM_APPLICATION.check(importedClasses);
        }

        if (configuration.activePackages().contains(ApplicationLayer.adapter)) {
            ADAPTER_LAYER_HAS_TO_RESPECT_HEXAGONAL_ARCHITECTURE.check(importedClasses);
        }

        NO_INJECTED_FIELDS.check(importedClasses);

        if (configuration.command().flatMap(CommandConfiguration::active).orElse(Boolean.TRUE)) {

            logger.info("Check Command rules");

            ONLY_CLASSES_WITH_COMMAND_ANNOTATION_ARE_ALLOWED.check(importedClasses);

            ALL_CLASSES_SHOULD_END_WITH_COMMAND_SUFFIX.check(importedClasses);

            ONLY_COMMAND_ANNOTATION_IS_ALLOWED_IN_RIGHT_PACKAGE.check(importedClasses);

        } else {

            logger.info("Check Command rules, no implementation, no import");

            COMMAND_ANNOTATION_IS_FORBIDDEN_WHEN_NO_IMPLEMENTATION.check(importedClasses);

        }

        if (configuration.query().flatMap(QueryConfiguration::active).orElse(Boolean.TRUE)) {

            logger.info("Check Query rules");

            ONLY_CLASSES_WITH_QUERY_ANNOTATION_ARE_ALLOWED.check(importedClasses);

            ALL_CLASSES_SHOULD_END_WITH_QUERY_SUFFIX.check(importedClasses);

            ONLY_QUERY_ANNOTATION_IS_ALLOWED_IN_RIGHT_PACKAGE.check(importedClasses);

        } else {

            logger.info("Check Query rules, no implementation, no import");

            QUERY_ANNOTATION_IS_FORBIDDEN_WHEN_NO_IMPLEMENTATION.check(importedClasses);

        }

        logger.info("Application is valid");
    }

}
