package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.client.impl.webclient.credential.BasicAuthorizationHeaderAppenderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeFilterBasicAuthorizationHeaderFactoryImplTest {

    @Mock
    private BasicAuthorizationHeaderAppenderImpl basicAuthorizationHeaderAppender;

    @InjectMocks
    private ExchangeFilterBasicAuthorizationHeaderFactoryImpl exchangeFilterBasicAuthorizationHeaderFactoryImpl;

    @Test
    void create() {
        assertDoesNotThrow(() -> {

            HttpMethod[] values = HttpMethod.values();

            ClientRequest clientRequest = ClientRequest
                .create(
                    values[Generator.randomInt(0, values.length - 1)],
                    URI.create("http://www." + Generator.generateAStringWithoutSpecialChars(1000) + "." + Generator.generateAStringWithoutSpecialChars(3))
                )
                .build();
            ExchangeFunction next = mock(ExchangeFunction.class);
            Mono<ClientResponse> clientResponse = mock(Mono.class);
            when(next.exchange(any(ClientRequest.class))).thenReturn(clientResponse);


            ExchangeFilterFunction actual = exchangeFilterBasicAuthorizationHeaderFactoryImpl.create();


            Mono<ClientResponse> actualClientResponse = actual.filter(clientRequest, next);


            assertThat(actualClientResponse)
                .isNotNull()
                .isSameAs(clientResponse);

            verify(next).exchange(any(ClientRequest.class));
            verify(basicAuthorizationHeaderAppender).execute(clientRequest.headers());

        });
    }
}