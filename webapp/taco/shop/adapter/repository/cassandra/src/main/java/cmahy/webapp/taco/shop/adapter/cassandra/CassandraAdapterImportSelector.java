package cmahy.webapp.taco.shop.adapter.cassandra;

import cmahy.webapp.taco.shop.adapter.cassandra.config.CassandraConfigurer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CassandraAdapterImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {CassandraConfigurer.class.getName()};
    }
}
