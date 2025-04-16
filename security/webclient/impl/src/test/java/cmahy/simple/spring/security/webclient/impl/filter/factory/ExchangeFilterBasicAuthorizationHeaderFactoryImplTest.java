package cmahy.simple.spring.security.webclient.impl.filter.factory;

import cmahy.simple.spring.security.webclient.impl.credential.BasicAuthorizationHeaderAppenderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExchangeFilterBasicAuthorizationHeaderFactoryImplTest {

    private static final URI DEFAULT_URL = URI.create("https://web-app.should-neverrrrrrrr-beeeeeeee-caaaaalled.be");

    @Mock
    private BasicAuthorizationHeaderAppenderImpl basicAuthorizationHeaderAppender;

    @InjectMocks
    private ExchangeFilterBasicAuthorizationHeaderFactoryImpl exchangeFilterBasicAuthorizationHeaderFactoryImpl;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            ClientRequest request = ClientRequest.create(HttpMethod.GET, DEFAULT_URL).build();
            ClientResponse response = mock(ClientResponse.class);
            ExchangeFunction exchange = r -> Mono.just(response);


            ExchangeFilterFunction authorizationFilter = exchangeFilterBasicAuthorizationHeaderFactoryImpl.create();

            ClientResponse actual = authorizationFilter.filter(request, exchange).block();


            assertThat(actual).isNotNull().isSameAs(response);

            verify(basicAuthorizationHeaderAppender).execute(any(HttpHeaders.class));
        });
    }
}