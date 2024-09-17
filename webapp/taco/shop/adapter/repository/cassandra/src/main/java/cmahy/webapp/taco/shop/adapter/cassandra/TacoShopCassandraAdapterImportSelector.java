package cmahy.webapp.taco.shop.adapter.cassandra;

import cmahy.webapp.taco.shop.adapter.cassandra.config.TacoShopCassandraConfigurer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class TacoShopCassandraAdapterImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {TacoShopCassandraConfigurer.class.getName()};
    }
}
