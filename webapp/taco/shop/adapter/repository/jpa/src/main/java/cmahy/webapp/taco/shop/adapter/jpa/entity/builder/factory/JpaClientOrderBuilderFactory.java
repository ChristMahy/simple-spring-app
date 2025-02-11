package cmahy.webapp.taco.shop.adapter.jpa.entity.builder.factory;

import cmahy.webapp.taco.shop.adapter.jpa.entity.domain.JpaClientOrder;
import cmahy.webapp.taco.shop.adapter.jpa.entity.builder.JpaClientOrderBuilder;
import cmahy.webapp.taco.shop.kernel.domain.builder.factory.ClientOrderBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaClientOrderBuilderFactory implements ClientOrderBuilderFactory<JpaClientOrder> {

    @Override
    public JpaClientOrderBuilder create() {
        return new JpaClientOrderBuilder();
    }

    @Override
    public JpaClientOrderBuilder create(JpaClientOrder clientOrder) {
        return new JpaClientOrderBuilder(clientOrder);
    }
}
