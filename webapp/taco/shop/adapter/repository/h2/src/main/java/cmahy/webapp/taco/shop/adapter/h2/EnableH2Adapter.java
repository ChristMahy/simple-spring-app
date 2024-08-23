package cmahy.webapp.taco.shop.adapter.h2;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ H2AdapterImportSelector.class })
public @interface EnableH2Adapter {
}
