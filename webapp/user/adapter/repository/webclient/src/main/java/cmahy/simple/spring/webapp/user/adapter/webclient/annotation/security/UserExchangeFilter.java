package cmahy.simple.spring.webapp.user.adapter.webclient.annotation.security;

import jakarta.inject.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface UserExchangeFilter {
}
