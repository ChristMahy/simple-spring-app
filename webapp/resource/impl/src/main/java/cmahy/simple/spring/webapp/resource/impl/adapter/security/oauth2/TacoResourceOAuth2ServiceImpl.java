package cmahy.simple.spring.webapp.resource.impl.adapter.security.oauth2;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.config.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("tacoResourceOAuth2Service")
public class TacoResourceOAuth2ServiceImpl implements TacoResourceOAuth2Service {

    private static final Logger LOG = LoggerFactory.getLogger(TacoResourceOAuth2ServiceImpl.class);

    private final Map<String, TacoResourceOAuth2Service> oauth2Services;

    public TacoResourceOAuth2ServiceImpl(OAuth2Properties oAuth2Properties, ApplicationContext applicationContext) {
        this.oauth2Services = mapClassToBean(oAuth2Properties, applicationContext);
    }

    private Map<String, TacoResourceOAuth2Service> mapClassToBean(OAuth2Properties oAuth2Properties, ApplicationContext applicationContext) {
        Map<String, TacoResourceOAuth2Service> oAuth2Services = new HashMap<>(oAuth2Properties.oAuth2ServiceConfigurer().size());

        oAuth2Properties.oAuth2ServiceConfigurer()
            .forEach((key, value) -> {
                Object bean = applicationContext.getBean(value);

                if (bean instanceof TacoResourceOAuth2Service oAuth2Service) {
                    oAuth2Services.put(key, oAuth2Service);
                } else {
                    throw new IllegalArgumentException(String.format("No bean found for key '%s'", key));
                }
            });

        return Collections.unmodifiableMap(oAuth2Services);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Optional<String> registrationId = Optional.ofNullable(userRequest)
            .map(OAuth2UserRequest::getClientRegistration)
            .map(ClientRegistration::getRegistrationId);

        if (registrationId.isPresent() && oauth2Services.containsKey(registrationId.get())) {
            return oauth2Services.get(registrationId.get()).loadUser(userRequest);
        }

        LOG.warn("No OAuth2 service found for registration id <{}>", registrationId.orElse("NONE"));

        throw new OAuth2AuthenticationException(new OAuth2Error("application-oauth2-misconfiguration"));
    }
}
