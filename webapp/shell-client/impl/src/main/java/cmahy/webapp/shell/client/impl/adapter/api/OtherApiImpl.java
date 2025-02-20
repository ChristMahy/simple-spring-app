package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.simple.spring.webapp.shell.client.api.OtherApi;
import cmahy.webapp.shell.client.impl.application.query.GenerateHelpMessageQuery;
import cmahy.webapp.shell.client.impl.application.query.PrintMessageQuery;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class OtherApiImpl extends OtherApi {

    private static final Logger LOG = LoggerFactory.getLogger(OtherApiImpl.class);

    private final PrintMessageQuery printMessageQuery;
    private final GenerateHelpMessageQuery generateHelpMessageQuery;

    public OtherApiImpl(
        PrintMessageQuery printMessageQuery,
        GenerateHelpMessageQuery generateHelpMessageQuery
    ) {
        this.printMessageQuery = printMessageQuery;
        this.generateHelpMessageQuery = generateHelpMessageQuery;
    }

    @Override
    public Integer call() {
        try {
            LOG.info("Print help menu started.");

            printMessageQuery.execute(
                generateHelpMessageQuery.execute().orElse(null)
            );

            LOG.info("Print help menu has finished successfully.");

            return 0;
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);

            return 1;
        }
    }
}
