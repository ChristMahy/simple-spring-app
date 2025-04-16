package cmahy.simple.spring.security.webclient.impl.filter.factory;

import cmahy.simple.spring.security.webclient.api.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.security.webclient.impl.credential.BasicAuthorizationHeaderAppenderImpl;
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
