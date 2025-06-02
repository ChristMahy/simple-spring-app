package cmahy.simple.spring.webapp.authorization.adapter.config.properties.security;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;
import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;
import java.util.Optional;

public record SecurityProperties(
    @Valid PublicKeyProperties publicKeys
) implements RSAPublicKeyConfigurationRepository {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("publicKeys", publicKeys())
            .toString();
    }

    @Override
    public Optional<String> getLocation(PublicKeyId id) {

        if (!publicKeys().locations().containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(publicKeys().locations().get(id));
    }

    @Override
    public Map<PublicKeyId, String> allLocations() {
        return publicKeys().locations();
    }

}
