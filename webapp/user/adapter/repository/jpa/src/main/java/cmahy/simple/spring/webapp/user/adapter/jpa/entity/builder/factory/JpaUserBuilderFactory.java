package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.JpaUserBuilder;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaUserBuilderFactory implements UserBuilderFactory<JpaUser> {

    @Override
    public JpaUserBuilder create() {
        return new JpaUserBuilder();
    }

    @Override
    public JpaUserBuilder create(JpaUser user) {
        return new JpaUserBuilder(user);
    }
}
