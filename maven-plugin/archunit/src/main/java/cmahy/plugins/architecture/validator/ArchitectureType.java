package cmahy.plugins.architecture.validator;

import cmahy.plugins.architecture.application.validator.ApplicationArchitectureValidator;
import cmahy.plugins.architecture.domain.validator.DomainArchitectureValidatorImpl;
import cmahy.plugins.architecture.library.validator.LibraryArchitectureValidatorImpl;
import cmahy.plugins.architecture.vo.validator.VoArchitectureValidatorImpl;

public enum ArchitectureType implements ArchitectureValidatorFactory {

    none(factoryContext -> new NoopArchitectureValidatorImpl(factoryContext.logger())),
    library(factoryContext -> new LibraryArchitectureValidatorImpl(factoryContext.projectConfiguration(), factoryContext.logger())),
    vo(factoryContext -> new VoArchitectureValidatorImpl(factoryContext.projectConfiguration(), factoryContext.logger())),
    application(factoryContext -> new ApplicationArchitectureValidator(factoryContext.projectConfiguration(), factoryContext.validatorConfiguration(), factoryContext.logger())),
    domain(factoryContext -> new DomainArchitectureValidatorImpl(factoryContext.projectConfiguration(), factoryContext.logger()));

    private final ArchitectureValidatorFactory factory;

    ArchitectureType(ArchitectureValidatorFactory factory) {
        this.factory = factory;
    }

    @Override
    public ArchitectureValidator create(ArchitectureValidatorFactoryContext factoryContext) {
        return factory.create(factoryContext);
    }

}
