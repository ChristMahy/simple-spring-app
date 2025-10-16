package cmahy.plugins.architecture.application.configuration;

import cmahy.plugins.architecture.configuration.ValidatorConfiguration;

import java.util.Collection;
import java.util.Optional;

public interface ApplicationValidatorConfiguration extends ValidatorConfiguration {

    Collection<ApplicationLayer> activePackages();

    Optional<? extends CommandConfiguration> command();

    Optional<? extends QueryConfiguration> query();

}