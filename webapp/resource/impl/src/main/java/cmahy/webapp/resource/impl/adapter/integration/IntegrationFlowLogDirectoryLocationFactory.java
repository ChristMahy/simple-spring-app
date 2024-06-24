package cmahy.webapp.resource.impl.adapter.integration;

import java.io.IOException;
import java.nio.file.Path;

public interface IntegrationFlowLogDirectoryLocationFactory {

    Path get() throws IOException;
}
