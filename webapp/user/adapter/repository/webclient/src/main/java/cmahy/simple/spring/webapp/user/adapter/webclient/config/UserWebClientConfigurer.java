package cmahy.simple.spring.webapp.user.adapter.webclient.config;

import cmahy.simple.spring.security.webclient.api.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.webapp.user.adapter.webclient.config.properties.user.SslOption;
import cmahy.simple.spring.webapp.user.adapter.webclient.config.properties.user.UserProperties;
import cmahy.simple.spring.webapp.user.adapter.webclient.config.properties.webclient.WebClientProperties;
import cmahy.simple.spring.webapp.user.adapter.webclient.annotation.security.UserExchangeFilter;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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

@AutoConfiguration
@ConditionalOnProperty(value = UserWebClientConfigurer.USER_WEBCLIENT_ACTIVE, havingValue = "true")
@ConfigurationPropertiesScan(basePackageClasses = {WebClientProperties.class, UserProperties.class})
@ComponentScan({
    "cmahy.simple.spring.webapp.user.adapter.webclient.repository",
    "cmahy.simple.spring.webapp.user.adapter.webclient.entity.id.factory"
})
public class UserWebClientConfigurer {
    protected static final String USER_WEBCLIENT_ACTIVE = "application.user.webclient.enabled";
    protected static final String SSL_ACTIVATION_PROPERTY_NAME = "application.user.external-resource.ssl.enabled";

    @Bean
    @ConditionalOnProperty(name = UserWebClientConfigurer.SSL_ACTIVATION_PROPERTY_NAME, havingValue = "false", matchIfMissing = true)
    public WebClient userResource(
        UserProperties userProperties,
        WebClientProperties webClientProperties,
        WebClient.Builder webClientBuilder,
        @UserExchangeFilter ExchangeFilterAuthorizationHeaderFactory exchangeFilterAuthorizationHeaderFactory
    ) {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Long.valueOf(webClientProperties.common().connectTimeout().toMillis()).intValue())
            .responseTimeout(webClientProperties.common().responseTimeout())
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(webClientProperties.common().readTimeout().toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(webClientProperties.common().writeTimeout().toMillis(), TimeUnit.MILLISECONDS))
            );

        return webClientBuilder
            .baseUrl(userProperties.externalResource().baseUrl())
            .filter(exchangeFilterAuthorizationHeaderFactory.create())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Bean
    @ConditionalOnProperty(name = UserWebClientConfigurer.SSL_ACTIVATION_PROPERTY_NAME, havingValue = "true")
    public WebClient userResourceSsl(
        UserProperties userProperties,
        WebClientProperties webClientProperties,
        WebClient.Builder webClientBuilder,
        SslContext userSslContext,
        @UserExchangeFilter ExchangeFilterAuthorizationHeaderFactory exchangeFilterAuthorizationHeaderFactory
    ) {
        HttpClient httpClient = HttpClient.create()
            .secure(sslContextSpec -> sslContextSpec.sslContext(userSslContext))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Long.valueOf(webClientProperties.common().connectTimeout().toMillis()).intValue())
            .responseTimeout(webClientProperties.common().responseTimeout())
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(webClientProperties.common().readTimeout().toMillis(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(webClientProperties.common().writeTimeout().toMillis(), TimeUnit.MILLISECONDS))
            );

        return webClientBuilder
            .baseUrl(userProperties.externalResource().baseUrl())
            .filter(exchangeFilterAuthorizationHeaderFactory.create())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Bean
    @ConditionalOnProperty(name = UserWebClientConfigurer.SSL_ACTIVATION_PROPERTY_NAME, havingValue = "true")
    public SslContext userSslContext(
        SslBundles sslBundles,
        UserProperties userProperties
    ) {
        SslBundle sslBundle = sslBundles.getBundle(
            Optional.ofNullable(userProperties.externalResource().ssl())
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
