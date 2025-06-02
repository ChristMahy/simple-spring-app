package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.client.impl.webclient.credential.BasicAuthorizationHeaderAppenderImpl;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class ExchangeFilterBasicAuthorizationHeaderFactoryImpl implements ExchangeFilterAuthorizationHeaderFactory {

    private final BasicAuthorizationHeaderAppenderImpl basicAuthorizationHeaderAppender;

    public ExchangeFilterBasicAuthorizationHeaderFactoryImpl(
        BasicAuthorizationHeaderAppenderImpl basicAuthorizationHeaderAppender
    ) {
        this.basicAuthorizationHeaderAppender = basicAuthorizationHeaderAppender;
    }

    @Override
    public ExchangeFilterFunction create() {
        return (request, next) -> next.exchange(
            ClientRequest
                .from(request)
                .headers(basicAuthorizationHeaderAppender::execute)
                .build()
        );
    }

}
