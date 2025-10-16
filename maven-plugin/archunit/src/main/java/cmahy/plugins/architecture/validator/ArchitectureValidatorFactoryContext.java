package cmahy.plugins.architecture.validator;

import cmahy.plugins.architecture.configuration.*;
import org.apache.maven.plugin.logging.Log;

public interface ArchitectureValidatorFactoryContext {

    Log logger();

    ProjectConfiguration projectConfiguration();

    ValidatorConfiguration validatorConfiguration();

}
