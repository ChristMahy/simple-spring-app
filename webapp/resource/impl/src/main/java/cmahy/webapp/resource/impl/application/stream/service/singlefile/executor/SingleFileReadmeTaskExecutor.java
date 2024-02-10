package cmahy.webapp.resource.impl.application.stream.service.singlefile.executor;

import java.io.IOException;
import java.io.OutputStream;

public interface SingleFileReadmeTaskExecutor {

    void execute(OutputStream outputStream) throws IOException;
}
