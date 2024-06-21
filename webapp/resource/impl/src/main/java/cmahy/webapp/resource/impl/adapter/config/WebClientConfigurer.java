package cmahy.webapp.resource.impl.adapter.config;

import cmahy.webapp.resource.impl.adapter.config.properties.webclient.WebClientProperties;
import cmahy.webapp.resource.impl.adapter.taco.shop.properties.ingredient.IngredientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Configuration
public class WebClientConfigurer {

    @Bean
    public WebClient tacoResource(
        IngredientProperties ingredientProperties,
        WebClientProperties webClientProperties
    ) {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Long.valueOf(webClientProperties.common().connectTimeout().toMillis()).intValue())
            .responseTimeout(webClientProperties.common().responseTimeout())
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(webClientProperties.common().readTimeout().toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(webClientProperties.common().writeTimeout().toMillis(), TimeUnit.MILLISECONDS))
            );

        return WebClient.builder()
            .baseUrl(ingredientProperties.externalResource().baseUrl())
            .filter(basicAuthentication(
                new String(webClientProperties.taco().credentials().username(), StandardCharsets.UTF_8),
                new String(webClientProperties.taco().credentials().password(), StandardCharsets.UTF_8)
            ))
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

}
