package cmahy.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.webapp.user.adapter.jpa.entity.JpaUser;
import cmahy.webapp.user.adapter.jpa.entity.builder.JpaUserBuilder;
import cmahy.webapp.user.kernel.domain.builder.factory.UserBuilderFactory;
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
