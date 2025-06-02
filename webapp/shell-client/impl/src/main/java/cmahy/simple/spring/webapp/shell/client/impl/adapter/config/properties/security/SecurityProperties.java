package cmahy.simple.spring.webapp.shell.client.impl.adapter.config.properties.security;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;
import jakarta.validation.Valid;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;
import java.util.Optional;

public record SecurityProperties(
    @Valid
    PrivateKeyLocationProperties privateKeys
) implements RSAPrivateKeyConfigurationRepository {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("privateKeys", privateKeys())
            .toString();
    }

    @Override
    public Optional<String> getLocation(PrivateKeyId id) {

        if (!privateKeys().locations().containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(privateKeys().locations().get(id));
    }

    @Override
    public Map<PrivateKeyId, String> allLocations() {
        return privateKeys().locations();
    }

}
