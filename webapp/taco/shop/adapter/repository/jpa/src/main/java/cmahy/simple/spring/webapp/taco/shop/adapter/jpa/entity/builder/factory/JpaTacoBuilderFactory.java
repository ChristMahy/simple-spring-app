package cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.builder.factory;

import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaTaco;
import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.builder.JpaTacoBuilder;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.builder.factory.TacoBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaTacoBuilderFactory implements TacoBuilderFactory<JpaTaco> {

    @Override
    public JpaTacoBuilder create() {
        return new JpaTacoBuilder();
    }

    @Override
    public JpaTacoBuilder create(JpaTaco taco) {
        return new JpaTacoBuilder(taco);
    }
}
