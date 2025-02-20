package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.simple.spring.webapp.shell.client.api.VersionApi;
import cmahy.webapp.shell.client.impl.application.query.GenerateVersionMessageQuery;
import cmahy.webapp.shell.client.impl.application.query.PrintMessageQuery;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class VersionApiImpl extends VersionApi {

    private static final Logger LOG = LoggerFactory.getLogger(VersionApiImpl.class);

    private final PrintMessageQuery printMessageQuery;
    private final GenerateVersionMessageQuery generateVersionMessageQuery;

    public VersionApiImpl(
        PrintMessageQuery printMessageQuery,
        GenerateVersionMessageQuery generateVersionMessageQuery
    ) {
        this.printMessageQuery = printMessageQuery;
        this.generateVersionMessageQuery = generateVersionMessageQuery;
    }

    @Override
    public Integer call() {
        try {
            LOG.info("Print version menu started.");

            printMessageQuery.execute(
                generateVersionMessageQuery.execute().orElse(null)
            );

            LOG.info("Print version menu has finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }
}
