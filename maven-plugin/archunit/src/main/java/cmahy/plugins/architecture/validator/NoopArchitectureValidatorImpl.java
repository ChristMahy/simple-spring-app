package cmahy.plugins.architecture.validator;

import org.apache.maven.plugin.logging.Log;

public class NoopArchitectureValidatorImpl implements ArchitectureValidator {

    private final Log logger;

    public NoopArchitectureValidatorImpl(Log logger) {
        this.logger = logger;
    }

    @Override
    public void validate() {
        logger.warn("Architecture validation is disabled");
    }

}
