package cmahy.plugins.architecture;

import cmahy.plugins.architecture.application.configuration.ApplicationValidatorConfigurationImpl;
import cmahy.plugins.architecture.configuration.*;
import cmahy.plugins.architecture.validator.*;
import org.apache.maven.plugin.*;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Mojo(
    name = "arch",
    threadSafe = true,
    defaultPhase = LifecyclePhase.VERIFY
)
public final class ArchitectureMojoImpl extends AbstractMojo {

    @Parameter(required = true)
    private Collection<ArchitectureType> types;

    @Parameter
    private ArchConfigurationImpl archConfiguration;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("Starting arch validation(s) <" + types.stream().map(ArchitectureType::name).collect(Collectors.joining(";")) + ">");

        getLog().info("Configurations; <" + archConfiguration + ">");

        Map<ArchitectureType, ValidatorConfiguration> validators = archConfiguration.validatorsGroupByType();

        for (ArchitectureType archType : types) {

            ValidatorConfiguration validatorConfiguration = validators
                .getOrDefault(archType, new ApplicationValidatorConfigurationImpl());

            getLog().info("Starting validation with <" + archType + ">");

            ArchitectureValidator validator = archType.create(new ArchitectureValidatorFactoryContext() {
                @Override
                public Log logger() {
                    return getLog();
                }

                @Override
                public ProjectConfiguration projectConfiguration() {
                    return archConfiguration.getProject();
                }

                @Override
                public ValidatorConfiguration validatorConfiguration() {
                    return validatorConfiguration;
                }
            });

            validator.validate();

            getLog().info("Validation with <" + archType + "> ended");

        }

        getLog().info("Arch validation(s) ended");

    }

}
