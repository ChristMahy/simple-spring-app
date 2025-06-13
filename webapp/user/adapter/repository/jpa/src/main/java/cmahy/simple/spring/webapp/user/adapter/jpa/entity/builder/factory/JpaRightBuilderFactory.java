package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.JpaRightBuilder;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaRight;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaRightBuilderFactory implements RightBuilderFactory<JpaRight> {

    @Override
    public JpaRightBuilder create() {
        return new JpaRightBuilder();
    }

    @Override
    public JpaRightBuilder create(JpaRight right) {
        return new JpaRightBuilder(right);
    }

}
