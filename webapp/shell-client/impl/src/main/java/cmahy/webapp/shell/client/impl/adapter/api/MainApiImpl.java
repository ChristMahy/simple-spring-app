package cmahy.webapp.shell.client.impl.adapter.api;

import cmahy.webapp.shell.client.api.MainApi;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class MainApiImpl extends MainApi {

    private static final Logger LOG = LoggerFactory.getLogger(MainApiImpl.class);

    @Override
    public Integer call() {
        try {
            LOG.info("Main app menu started.");

            LOG.info("Main app menu has been finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }
    }
}
