package cmahy.simple.spring.security.client.impl.webclient.filter.factory;

import cmahy.simple.spring.common.helper.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeFilterNoAuthorizationHeaderFactoryImplTest {

    @InjectMocks
    private ExchangeFilterNoAuthorizationHeaderFactoryImpl exchangeFilterNoAuthorizationHeaderFactoryImpl;

    @Test
    void create() {
        assertDoesNotThrow(() -> {

            ClientRequest clientRequest = mock(ClientRequest.class);
            ExchangeFunction next = mock(ExchangeFunction.class);
            Mono<ClientResponse> clientResponse = mock(Mono.class);
            when(next.exchange(clientRequest)).thenReturn(clientResponse);


            ExchangeFilterFunction actual = exchangeFilterNoAuthorizationHeaderFactoryImpl.create();


            Mono<ClientResponse> actualClientResponse = actual.filter(clientRequest, next);


            assertThat(actualClientResponse)
                .isNotNull()
                .isSameAs(clientResponse);

            verifyNoInteractions(clientRequest);
            verify(next).exchange(clientRequest);

        });
    }
}