package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.util.integration.test;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.*;

public class PropertyOverriderApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        if (Objects.isNull(BackEndStub.INSTANCE.getMockBackEnd())) {
            throw new  IllegalStateException("Mock back end is not started !!!");
        }

        ConfigurableEnvironment configurableEnvironment = event.getEnvironment();

        Map<String, Object> overrideProperties = new HashMap<>();
        overrideProperties.put(
            "back-end-stub",
            "http://" + BackEndStub.INSTANCE.getMockBackEnd().getHostName() + ":" + BackEndStub.INSTANCE.getMockBackEnd().getPort()
        );

        MapPropertySource propertySource = new MapPropertySource("configurationPropertiesOverride", overrideProperties);
        configurableEnvironment.getPropertySources().addFirst(propertySource);
    }
}
