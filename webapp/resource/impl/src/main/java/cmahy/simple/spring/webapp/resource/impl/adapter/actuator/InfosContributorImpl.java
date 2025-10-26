package cmahy.simple.spring.webapp.resource.impl.adapter.actuator;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.info.InfosProperties;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class InfosContributorImpl implements InfoContributor {

    private final InfosProperties infosProperties;

    public InfosContributorImpl(InfosProperties infosProperties) {
        this.infosProperties = infosProperties;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetails(infosProperties.toMap());
    }

}
