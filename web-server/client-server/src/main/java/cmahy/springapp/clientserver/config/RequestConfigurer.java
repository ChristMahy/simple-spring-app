package cmahy.springapp.clientserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RequestConfigurer {
    @Bean
    public RestTemplate oauth2RestTemplate(
        OAuth2AuthorizedClientService clientService
    ) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate = applyBearerTokenInterceptor(restTemplate, clientService);

        return restTemplate;
    }

    private RestTemplate applyBearerTokenInterceptor(
        RestTemplate restTemplate,
        OAuth2AuthorizedClientService clientService
    ) {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            final String accessToken;

            if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
                OAuth2AuthenticationToken oAuthToken = (OAuth2AuthenticationToken) authentication;

                String clientRegistrationId = oAuthToken.getAuthorizedClientRegistrationId();

                if (clientRegistrationId.equals("taco-admin-client-oidc")) {
                    OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                        clientRegistrationId, oAuthToken.getName()
                    );

                    accessToken = client.getAccessToken().getTokenValue();
                } else {
                    accessToken = null;
                }
            } else {
                accessToken = null;
            }

            request.getHeaders().add("Authorization", "Bearer " + accessToken);

            return execution.execute(request, body);
        };

        restTemplate.getInterceptors().add(interceptor);

        return restTemplate;
    }
}
