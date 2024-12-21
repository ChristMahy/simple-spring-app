package cmahy.webapp.user.adapter.jpa.entity.builder.factory;

import cmahy.webapp.user.adapter.jpa.entity.JpaRole;
import cmahy.webapp.user.adapter.jpa.entity.builder.JpaRoleBuilder;
import cmahy.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import jakarta.inject.Named;
import org.springframework.context.annotation.Primary;

@Primary
@Named
public class JpaRoleBuilderFactory implements RoleBuilderFactory<JpaRole> {

    @Override
    public JpaRoleBuilder create() {
        return new JpaRoleBuilder();
    }

    @Override
    public JpaRoleBuilder create(JpaRole role) {
        return new JpaRoleBuilder(role);
    }
}
