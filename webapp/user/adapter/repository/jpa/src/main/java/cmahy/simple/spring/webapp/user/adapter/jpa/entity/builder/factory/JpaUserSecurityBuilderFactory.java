package cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.simple.spring.webapp.user.adapter.jpa.entity.builder.JpaUserSecurityBuilder;
import cmahy.simple.spring.webapp.user.adapter.jpa.entity.domain.JpaUserSecurity;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaUserSecurityBuilderFactory implements UserSecurityBuilderFactory<JpaUserSecurity> {

    @Override
    public JpaUserSecurityBuilder create() {
        return new JpaUserSecurityBuilder();
    }

    @Override
    public JpaUserSecurityBuilder create(JpaUserSecurity userSecurity) {
        return new JpaUserSecurityBuilder(userSecurity);
    }
}
