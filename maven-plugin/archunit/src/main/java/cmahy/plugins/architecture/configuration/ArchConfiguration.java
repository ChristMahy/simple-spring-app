package cmahy.plugins.architecture.configuration;

import cmahy.plugins.architecture.validator.ArchitectureType;

import java.util.Collection;
import java.util.Map;

public interface ArchConfiguration {

    ProjectConfiguration project();

    Collection<? extends ValidatorConfiguration> validators();

    Map<ArchitectureType, ValidatorConfiguration> validatorsGroupByType();

}
