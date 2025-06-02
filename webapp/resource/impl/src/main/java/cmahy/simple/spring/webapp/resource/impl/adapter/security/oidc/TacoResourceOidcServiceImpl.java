package cmahy.simple.spring.webapp.resource.impl.adapter.security.oidc;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("tacoResourceOidcService")
public class TacoResourceOidcServiceImpl implements TacoResourceOidcService {

    private static final Logger LOG = LoggerFactory.getLogger(TacoResourceOidcServiceImpl.class);

    private final Map<String, TacoResourceOidcService> oidcServices;

    public TacoResourceOidcServiceImpl(OAuth2Properties oAuth2Properties, List<TacoResourceOidcService> oidcServices) {
        this.oidcServices = mapClassToBean(oAuth2Properties, oidcServices);
    }

    private Map<String, TacoResourceOidcService> mapClassToBean(OAuth2Properties oAuth2Properties, List<TacoResourceOidcService> allOidcServices) {

        return Optional.ofNullable(oAuth2Properties.oidcServiceConfigurer())
            .filter(oidcServiceConfigurer -> !oidcServiceConfigurer.isEmpty())
            .map(oidcServiceConfigurer -> {
                Map<String, TacoResourceOidcService> oidcServices = new HashMap<>(oidcServiceConfigurer.size());

                oidcServiceConfigurer.forEach((key, value) -> {
                    List<TacoResourceOidcService> beans = allOidcServices.stream()
                        .filter(oidcService -> oidcService.getClass().equals(value))
                        .toList();

                    if (beans.size() != 1) {
                        throw new IllegalArgumentException(String.format("<%d> bean(s) found for key <%s>", beans.size(), key));
                    }

                    oidcServices.put(key, beans.getFirst());
                });

                return oidcServices;
            })
            .map(Collections::unmodifiableMap)
            .orElseGet(Collections::emptyMap);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        Optional<String> registrationId = Optional.ofNullable(userRequest)
            .map(OidcUserRequest::getClientRegistration)
            .map(ClientRegistration::getRegistrationId);

        if (registrationId.isPresent() && oidcServices.containsKey(registrationId.get())) {
            return oidcServices.get(registrationId.get()).loadUser(userRequest);
        }

        LOG.warn("No Oidc service found for registration id <{}>", registrationId.orElse("NONE"));

        throw new OAuth2AuthenticationException(new OAuth2Error("application-oidc-misconfiguration"));
    }
}
