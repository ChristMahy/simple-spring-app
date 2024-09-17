package cmahy.webapp.taco.shop.kernel.application.repository.proxy.annotation;

import jakarta.inject.Qualifier;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Documented
@Qualifier
public @interface RemoteOrDefault {
}
