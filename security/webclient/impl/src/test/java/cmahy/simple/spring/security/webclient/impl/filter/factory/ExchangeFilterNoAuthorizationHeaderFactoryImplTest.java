package cmahy.simple.spring.security.webclient.impl.filter.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExchangeFilterNoAuthorizationHeaderFactoryImplTest {

    private static final URI DEFAULT_URL = URI.create("https://web-app.should-neverrrrrrrr-beeeeeeee-caaaaalled.be");

    @InjectMocks
    private ExchangeFilterNoAuthorizationHeaderFactoryImpl exchangeFilterNoAuthorizationHeaderFactoryImpl;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            ClientRequest request = ClientRequest.create(HttpMethod.GET, DEFAULT_URL).build();
            ClientResponse response = mock(ClientResponse.class);
            ExchangeFunction exchange = r -> Mono.just(response);


            ExchangeFilterFunction authorizationFilter = exchangeFilterNoAuthorizationHeaderFactoryImpl.create();

            ClientResponse actual = authorizationFilter.filter(request, exchange).block();


            assertThat(actual).isNotNull().isSameAs(response);
        });
    }
}