package cmahy.webapp.resource.impl.adapter.security.request.userinfo.google;

import cmahy.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleUserInfoRequestFactory {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleUserInfoRequestFactory.class);

    public RestTemplate create(UserInfoRequestConfig requestConfig) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(
            requestConfig.accessToken().map(accessToken -> StringUtils.isBlank(accessToken) ? null : accessToken)
                .map(this::getBearerInterceptor)
                .orElseGet(this::getNoTokenInterceptor)
        );

        return restTemplate;
    }

    private ClientHttpRequestInterceptor getBearerInterceptor(String accessToken) {
        return (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);

            LOG.info("Setting up headers <{}>", accessToken);
            LOG.info("<{}>", request.getURI().toURL());

            return execution.execute(request, body);
        };
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return (request, body, execution) -> {
            throw new IllegalArgumentException("Access token is missing.");
        };
    }
}
