package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.annotation.security;

import jakarta.inject.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface TacoShopExchangeFilter {
}
