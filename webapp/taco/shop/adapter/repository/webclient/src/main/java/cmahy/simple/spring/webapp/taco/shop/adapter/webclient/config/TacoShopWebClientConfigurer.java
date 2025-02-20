package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config;

import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.primary.*;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.primary.*;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.ingredient.IngredientProperties;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.ingredient.SslOption;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.webclient.WebClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@AutoConfiguration
@ConditionalOnProperty(value = TacoShopWebClientConfigurer.TACO_SHOP_WEBCLIENT_ACTIVE, havingValue = "true")
@ConfigurationPropertiesScan(basePackageClasses = {WebClientProperties.class, IngredientProperties.class})
@ComponentScan({
    "cmahy.simple.spring.webapp.taco.shop.adapter.webclient.repository",
    "cmahy.simple.spring.webapp.taco.shop.adapter.webclient.entity.builder.factory"
})
@Import({
    ClientOrderRepositoryPrimaryConfigurer.class,
    ClientOrderPagingRepositoryPrimaryConfigurer.class,
    IngredientRepositoryPrimaryConfigurer.class,
    IngredientPagingRepositoryPrimaryConfigurer.class,
    TacoRepositoryPrimaryConfigurer.class
})
public class TacoShopWebClientConfigurer {

    protected static final String TACO_SHOP_WEBCLIENT_ACTIVE = "application.taco-shop.webclient.enabled";
    protected static final String SSL_ACTIVATION_PROPERTY_NAME = "application.taco.ingredients.external-resource.ssl.enabled";

    @Bean(name = "tacoResource")
    @ConditionalOnProperty(name = TacoShopWebClientConfigurer.SSL_ACTIVATION_PROPERTY_NAME, havingValue = "false", matchIfMissing = true)
    public WebClient tacoResource(
        IngredientProperties ingredientProperties,
        WebClientProperties webClientProperties,
        WebClient.Builder webClientBuilder
    ) {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Long.valueOf(webClientProperties.common().connectTimeout().toMillis()).intValue())
            .responseTimeout(webClientProperties.common().responseTimeout())
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(webClientProperties.common().readTimeout().toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(webClientProperties.common().writeTimeout().toMillis(), TimeUnit.MILLISECONDS))
            );

        return webClientBuilder
            .baseUrl(ingredientProperties.externalResource().baseUrl())
            .filter(basicAuthentication(
                webClientProperties.taco().credentials().usernameToString(),
                webClientProperties.taco().credentials().passwordToString()
            ))
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Bean(name = "tacoResource")
    @ConditionalOnProperty(name = TacoShopWebClientConfigurer.SSL_ACTIVATION_PROPERTY_NAME, havingValue = "true")
    public WebClient tacoResourceSsl(
        IngredientProperties ingredientProperties,
        WebClientProperties webClientProperties,
        WebClient.Builder webClientBuilder,
        SslContext tacoSslContext
    ) {
        HttpClient httpClient = HttpClient.create()
            .secure(sslContextSpec -> sslContextSpec.sslContext(tacoSslContext))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Long.valueOf(webClientProperties.common().connectTimeout().toMillis()).intValue())
            .responseTimeout(webClientProperties.common().responseTimeout())
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(webClientProperties.common().readTimeout().toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(webClientProperties.common().writeTimeout().toMillis(), TimeUnit.MILLISECONDS))
            );

        return webClientBuilder
            .baseUrl(ingredientProperties.externalResource().baseUrl())
            .filter(basicAuthentication(
                webClientProperties.taco().credentials().usernameToString(),
                webClientProperties.taco().credentials().passwordToString()
            ))
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Bean
    @ConditionalOnProperty(name = TacoShopWebClientConfigurer.SSL_ACTIVATION_PROPERTY_NAME, havingValue = "true")
    public SslContext tacoSslContext(
        SslBundles sslBundles,
        IngredientProperties ingredientProperties
    ) {
        SslBundle sslBundle = sslBundles.getBundle(
            Optional.ofNullable(ingredientProperties.externalResource().ssl())
                .map(SslOption::bundleName)
                .filter(StringUtils::isNotBlank)
                .orElse("brol")
        );

        try {
            final KeyStore trustStore = sslBundle.getStores().getTrustStore();

            List<Certificate> certificates = Collections.list(trustStore.aliases())
                .stream()
                .filter(t -> {
                    try {
                        return trustStore.isCertificateEntry(t);
                    } catch (KeyStoreException e1) {
                        throw new RuntimeException("Error reading truststore", e1);
                    }
                })
                .map(t -> {
                    try {
                        return trustStore.getCertificate(t);
                    } catch (KeyStoreException e2) {
                        throw new RuntimeException("Error reading truststore", e2);
                    }
                })
                .toList();

            return SslContextBuilder.forClient()
                .trustManager(certificates.toArray(new X509Certificate[certificates.size()]))
                .build();
        } catch (KeyStoreException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
