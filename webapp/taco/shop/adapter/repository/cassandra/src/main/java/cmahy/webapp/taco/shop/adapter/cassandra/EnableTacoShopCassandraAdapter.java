package cmahy.webapp.taco.shop.adapter.cassandra;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ TacoShopCassandraAdapterImportSelector.class })
public @interface EnableTacoShopCassandraAdapter {
}
