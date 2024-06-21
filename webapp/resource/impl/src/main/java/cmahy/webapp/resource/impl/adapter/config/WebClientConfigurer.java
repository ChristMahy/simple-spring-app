package cmahy.webapp.resource.impl.adapter.config;

import cmahy.webapp.resource.impl.adapter.taco.shop.properties.ingredient.IngredientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Configuration
public class WebClientConfigurer {

    @Bean
    public WebClient tacoResource(
        IngredientProperties ingredientProperties,
        @Value("${application.web-client.common.connect-timeout}")
        Duration connectTimeout,
        @Value("${application.web-client.common.response-timeout}")
        Duration responseTimeout,
        @Value("${application.web-client.common.read-timeout}")
        Duration readTimeout,
        @Value("${application.web-client.common.write-timeout}")
        Duration writeTimeout,
        @Value("${application.web-client.taco.username}")
        byte[] credentialUsername,
        @Value("${application.web-client.taco.password}")
        byte[] credentialPassword
    ) {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Long.valueOf(connectTimeout.toMillis()).intValue())
            .responseTimeout(responseTimeout)
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(readTimeout.toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(writeTimeout.toMillis(), TimeUnit.MILLISECONDS))
            );

        return WebClient.builder()
            .baseUrl(ingredientProperties.externalResource().baseUrl())
            .filter(basicAuthentication(new String(credentialUsername, StandardCharsets.UTF_8), new String(credentialPassword, StandardCharsets.UTF_8)))
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

}
