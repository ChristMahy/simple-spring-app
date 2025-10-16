package cmahy.plugins.architecture.vo.validator;

import cmahy.plugins.architecture.configuration.ProjectConfiguration;
import cmahy.plugins.architecture.validator.ArchitectureValidator;
import cmahy.plugins.architecture.rule.MainCodingRule;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.apache.maven.plugin.logging.Log;

import java.nio.file.Path;
import java.nio.file.Paths;

import static cmahy.plugins.architecture.vo.rule.VoArchRule.ALL_FIELDS_OF_VO_HAVE_TO_BE_FINAL;

public class VoArchitectureValidatorImpl implements ArchitectureValidator {

    private final ProjectConfiguration projectConfiguration;
    private final Log logger;

    public VoArchitectureValidatorImpl(
        ProjectConfiguration projectConfiguration, Log logger
    ) {
        this.projectConfiguration = projectConfiguration;
        this.logger = logger;
    }

    @Override
    public void validate() {

        Path targetClasses = Paths.get(projectConfiguration.build().directory(), "classes");

        JavaClasses importedClasses = new ClassFileImporter(MainCodingRule.IMPORTS_OPTIONS)
            .importPath(targetClasses);

        logger.info("Imported classes <" + importedClasses.size() + ">");

        ALL_FIELDS_OF_VO_HAVE_TO_BE_FINAL.check(importedClasses);

        logger.info( "VOs are valid");

    }

}
