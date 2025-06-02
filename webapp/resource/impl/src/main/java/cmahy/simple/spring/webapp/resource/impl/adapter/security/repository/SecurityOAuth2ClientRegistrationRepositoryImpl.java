package cmahy.simple.spring.webapp.resource.impl.adapter.security.repository;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.vo.output.OAuth2RegistrationOutputVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SecurityOAuth2ClientRegistrationRepositoryImpl implements SecurityOAuth2ClientRegistrationRepository {

    private final Optional<OAuth2ClientProperties> oAuth2ClientProperties;

    public SecurityOAuth2ClientRegistrationRepositoryImpl(
        Optional<OAuth2ClientProperties> oAuth2ClientProperties
    ) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
    }

    @Override
    public Collection<OAuth2RegistrationOutputVo> findAllAvailableRegistrations() {

        if (oAuth2ClientProperties.isEmpty()) {
            return Collections.emptyList();
        }

        return oAuth2ClientProperties
            .get()
            .getRegistration()
            .entrySet()
            .stream()
            .filter(entry -> AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(
                entry.getValue().getAuthorizationGrantType()
            ))
            .map(registration -> new OAuth2RegistrationOutputVo(
                registration.getKey(),
                Optional.ofNullable(registration.getValue().getClientName())
                    .filter(StringUtils::isNotBlank)
                    .orElse(registration.getKey())
            ))
            .toList();

    }

}
