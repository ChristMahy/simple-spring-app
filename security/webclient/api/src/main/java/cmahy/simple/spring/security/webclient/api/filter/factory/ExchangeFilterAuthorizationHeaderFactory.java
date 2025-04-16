package cmahy.simple.spring.security.webclient.api.filter.factory;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public interface ExchangeFilterAuthorizationHeaderFactory {

    ExchangeFilterFunction create();

}
