package cmahy.webapp.taco.shop.adapter.h2;

import cmahy.webapp.taco.shop.adapter.h2.config.H2Configurer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class H2AdapterImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {H2Configurer.class.getName() };
    }
}
