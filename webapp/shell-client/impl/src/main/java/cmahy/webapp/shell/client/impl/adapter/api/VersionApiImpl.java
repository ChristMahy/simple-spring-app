package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.webapp.shell.client.api.VersionApi;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.info.BuildProperties;

@Named
public class VersionApiImpl extends VersionApi {

    private static final Logger LOG = LoggerFactory.getLogger(VersionApiImpl.class);

    private final BuildProperties buildProperties;

    public VersionApiImpl(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Override
    public Integer call() {
        try {
            LOG.info("Print version menu started.");

//            printMsg.execute(generateVersionMessageService.execute())

            LOG.info(
                ":: {}.{}.{} :: {} :: Java {} :: {} ::",
                StringUtils.defaultIfBlank(buildProperties.getGroup(), "NONE"),
                StringUtils.defaultIfBlank(buildProperties.get("project.name"), "NONE"),
                StringUtils.defaultIfBlank(buildProperties.getName(), "NONE"),
                StringUtils.defaultIfBlank(buildProperties.getVersion(), "NONE"),
                StringUtils.defaultIfBlank(buildProperties.get("java.version"), "NONE"),
                StringUtils.defaultIfBlank(buildProperties.get("description"), "NONE")
            );

            LOG.info("Print version menu has finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }
}
