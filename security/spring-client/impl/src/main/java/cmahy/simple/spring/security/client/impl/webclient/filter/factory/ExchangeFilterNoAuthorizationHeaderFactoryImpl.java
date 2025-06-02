package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class ExchangeFilterNoAuthorizationHeaderFactoryImpl implements ExchangeFilterAuthorizationHeaderFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeFilterNoAuthorizationHeaderFactoryImpl.class);

    @Override
    public ExchangeFilterFunction create() {
        return (request, next) -> {
            LOG.debug("Exchange filter default authorization is used, no authorization header will be added");

            return next.exchange(request);
        };
    }

}
