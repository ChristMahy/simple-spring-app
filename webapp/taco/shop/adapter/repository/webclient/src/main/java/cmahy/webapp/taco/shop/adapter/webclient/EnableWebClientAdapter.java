package cmahy.webapp.taco.shop.adapter.webclient;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebClientAdapterImportSelector.class})
public @interface EnableWebClientAdapter {
}
