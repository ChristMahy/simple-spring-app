package cmahy.simple.spring.security.client.api.webclient.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.util.MultiValueMap;

public interface PrivateKeyJwtRequestParametersConverter extends Converter<OAuth2ClientCredentialsGrantRequest, MultiValueMap<String, String>> {
}
