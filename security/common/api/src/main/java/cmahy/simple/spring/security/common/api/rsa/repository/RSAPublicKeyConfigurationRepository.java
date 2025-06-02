package cmahy.simple.spring.security.common.api.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;

import java.util.Map;
import java.util.Optional;

public interface RSAPublicKeyConfigurationRepository {

    Optional<String> getLocation(PublicKeyId id);

    Map<PublicKeyId, String> allLocations();

}
