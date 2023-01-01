package cmahy.springapp.config.security;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnOAuth2Condition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        BeanDefinition beanDefinition = context.getRegistry()
            .getBeanDefinition(OAuthProperty.class.getSimpleName());

        return false;
    }
}
