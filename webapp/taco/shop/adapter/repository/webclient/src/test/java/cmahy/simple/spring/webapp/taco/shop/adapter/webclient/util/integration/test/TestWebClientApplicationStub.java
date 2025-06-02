package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.util.integration.test;

import cmahy.simple.spring.common.converter.StringToByteConverter;
import cmahy.simple.spring.common.converter.factory.StringToByteConverterFactory;
import cmahy.simple.spring.security.client.api.webclient.filter.factory.ExchangeFilterAuthorizationHeaderFactory;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.annotation.security.TacoShopExchangeFilter;
import cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.TacoShopWebClientConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Configuration
@EnableAutoConfiguration
@Import({TacoShopWebClientConfigurer.class})
public class TestWebClientApplicationStub {

    @Bean
    public StringToByteConverter stringToBytesConverterImpl() {
        return StringToByteConverterFactory.create();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public Converter<String, byte[]> stringToBytesConverter(StringToByteConverter converter) {
        return new Converter<>() {
            @Override
            public byte[] convert(String source) {
                return converter.convert(source);
            }
        };
    }

    @Bean
    @TacoShopExchangeFilter
    public ExchangeFilterAuthorizationHeaderFactory exchangeFilterAuthorizationHeaderFactory() {
        return new ExchangeFilterAuthorizationHeaderFactory() {
            @Override
            public ExchangeFilterFunction create() {
                return (request, next) -> next.exchange(request);
            }
        };
    }
}
