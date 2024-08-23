package cmahy.webapp.taco.shop.adapter.webclient;

import cmahy.webapp.taco.shop.adapter.webclient.config.WebClientConfigurer;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class WebClientAdapterImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] { WebClientConfigurer.class.getName() };
    }
}
