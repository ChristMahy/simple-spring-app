package cmahy.webapp.resource.impl.adapter.security.request.userinfo.google;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.security.request.userinfo.vo.input.UserInfoRequestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleUserInfoRequestFactoryTest {

    @InjectMocks
    private GoogleUserInfoRequestFactory googleUserInfoRequestFactory;

    @Test
    void create_whenHasAnAccessToken_thenAddInterceptorWithBearerAuthentication() {
        assertDoesNotThrow(() -> {
            UserInfoRequestConfig configs = new UserInfoRequestConfig(
                Optional.of(Generator.generateAString())
            );

            RestTemplate actual = googleUserInfoRequestFactory.create(configs);

            assertThat(actual).isNotNull();

            assertThat(actual.getInterceptors()).hasSize(1);

            HttpRequest request = mock(HttpRequest.class, RETURNS_DEEP_STUBS);
            byte[] body = Generator.randomBytes(100);
            ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);

            ClientHttpResponse response = mock(ClientHttpResponse.class);
            when(execution.execute(request, body)).thenReturn(response);

            ClientHttpResponse actualResponse = actual.getInterceptors().stream().findFirst().get().intercept(request, body, execution);

            assertThat(actualResponse).isNotNull().isSameAs(response);

            verify(request.getHeaders()).add("Authorization", "Bearer " + configs.accessToken().get());
        });
    }

    @Test
    void create_whenNoAccessToken_thenAddInterceptorWithoutAuthenticationWhichShouldThrowAnException() {
        assertDoesNotThrow(() -> {
            UserInfoRequestConfig configs = new UserInfoRequestConfig(
                Optional.empty()
            );

            RestTemplate actual = googleUserInfoRequestFactory.create(configs);

            assertThat(actual).isNotNull();

            assertThat(actual.getInterceptors()).hasSize(1);

            HttpRequest request = mock(HttpRequest.class, RETURNS_DEEP_STUBS);
            byte[] body = Generator.randomBytes(100);
            ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);

            assertThrows(IllegalArgumentException.class, () -> {
                actual.getInterceptors().stream().findFirst().get().intercept(request, body, execution);
            });

            verify(request.getHeaders(), never()).add(anyString(), anyString());
        });
    }
}