package cmahy.plugins.architecture.validator;

public interface ArchitectureValidatorFactory {

    ArchitectureValidator create(ArchitectureValidatorFactoryContext factoryContext);

}
