package cmahy.simple.spring.security.client.api.webclient.filter.factory;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public interface ExchangeFilterAuthorizationHeaderFactory {

    ExchangeFilterFunction create();

}
