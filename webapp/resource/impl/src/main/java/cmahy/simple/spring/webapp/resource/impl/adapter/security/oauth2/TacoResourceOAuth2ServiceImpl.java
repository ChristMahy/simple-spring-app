package cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("tacoResourceOAuth2Service")
public class TacoResourceOAuth2ServiceImpl implements TacoResourceOAuth2Service {

    private static final Logger LOG = LoggerFactory.getLogger(TacoResourceOAuth2ServiceImpl.class);

    private final Map<String, TacoResourceOAuth2Service> oAuth2Services;

    public TacoResourceOAuth2ServiceImpl(OAuth2Properties oAuth2Properties, List<TacoResourceOAuth2Service> oAuth2Services) {
        this.oAuth2Services = mapClassToBean(oAuth2Properties, oAuth2Services);
    }

    private Map<String, TacoResourceOAuth2Service> mapClassToBean(OAuth2Properties oAuth2Properties, List<TacoResourceOAuth2Service> allOAuth2Services) {

        return Optional.ofNullable(oAuth2Properties.oAuth2ServiceConfigurer())
            .filter(oAuth2ServiceConfigurer -> !oAuth2ServiceConfigurer.isEmpty())
            .map(oAuth2ServiceConfigurer -> {
                Map<String, TacoResourceOAuth2Service> oAuth2Services = new HashMap<>(oAuth2ServiceConfigurer.size());

                oAuth2ServiceConfigurer.forEach((key, value) -> {
                    List<TacoResourceOAuth2Service> beans = allOAuth2Services.stream()
                        .filter(oAuth2Service -> oAuth2Service.getClass().equals(value))
                        .toList();

                    if (beans.size() != 1) {
                        throw new IllegalArgumentException(String.format("<%d> beans found for key <%s>", beans.size(), key));
                    }

                    oAuth2Services.put(key, beans.getFirst());
                });

                return oAuth2Services;
            })
            .map(Collections::unmodifiableMap)
            .orElseGet(Collections::emptyMap);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Optional<String> registrationId = Optional.ofNullable(userRequest)
            .map(OAuth2UserRequest::getClientRegistration)
            .map(ClientRegistration::getRegistrationId);

        if (registrationId.isPresent() && oAuth2Services.containsKey(registrationId.get())) {
            return oAuth2Services.get(registrationId.get()).loadUser(userRequest);
        }

        LOG.warn("No OAuth2 service found for registration id <{}>", registrationId.orElse("NONE"));

        throw new OAuth2AuthenticationException(new OAuth2Error("application-oauth2-misconfiguration"));
    }
}
