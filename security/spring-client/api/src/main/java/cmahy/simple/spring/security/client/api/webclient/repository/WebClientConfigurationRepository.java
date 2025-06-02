package cmahy.simple.spring.security.client.api.webclient.repository;

import java.util.Optional;

public interface WebClientConfigurationRepository {

    Optional<String> defaultRegistrationId();
    
}
