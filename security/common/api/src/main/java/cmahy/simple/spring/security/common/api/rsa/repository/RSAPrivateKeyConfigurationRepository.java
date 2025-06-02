package cmahy.simple.spring.security.common.api.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;

import java.util.Map;
import java.util.Optional;

public interface RSAPrivateKeyConfigurationRepository {

    Optional<String> getLocation(PrivateKeyId id);

    Map<PrivateKeyId, String> allLocations();

}
