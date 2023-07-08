package cmahy.springapp.clientserver.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class RequestConfigurer {

    private final static Logger LOG = getLogger(RequestConfigurer.class);

    @Bean
    public WebClient oauth2WebClient(
        @Value("${app.resource-server.base-url}") String resourceBaseUrl,
        ReactiveClientRegistrationRepository clientRegistration,
        ServerOAuth2AuthorizedClientRepository authorizedClient
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
            clientRegistration,
            authorizedClient
        );

        oauth2.setDefaultOAuth2AuthorizedClient(true);

        return WebClient.builder()
            .baseUrl(resourceBaseUrl)
            .filter(oauth2)
            .filter(logRequest())
            .filter(logResponse())
            .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (LOG.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");

                sb.append("Method=<" + clientRequest.method() + ">, URL=<" + clientRequest.url() + ">\n");

                clientRequest
                    .headers()
                    .forEach((name, values) -> {
                        values.forEach(value -> {
                            sb
                                .append("Header ")
                                .append("<" + name + ">")
                                .append(" has ")
                                .append("<" + values + ">")
                                .append('\n');
                        });
                    });

                LOG.debug(sb.toString());
            }

            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (LOG.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Response: \n");

                sb.append("Status=<" + clientResponse.statusCode() + ">\n");

                clientResponse
                    .headers()
                    .asHttpHeaders()
                    .forEach((name, values) -> {
                        values.forEach(value -> {
                            sb
                                .append("Header ")
                                .append("<" + name + ">")
                                .append(" has ")
                                .append("<" + values + ">")
                                .append('\n');
                        });
                    });

                LOG.debug(sb.toString());
            }

            return Mono.just(clientResponse);
        });
    }
}
