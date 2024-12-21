package cmahy.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.webapp.user.adapter.jpa.entity.JpaUserSecurity;
import cmahy.webapp.user.adapter.jpa.entity.builder.JpaUserSecurityBuilder;
import cmahy.webapp.user.kernel.domain.builder.factory.UserSecurityBuilderFactory;
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
