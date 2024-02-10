package cmahy.webapp.resource.impl.application.stream.service.zip.executor;

import cmahy.webapp.resource.impl.application.stream.exception.EmptyZipEntryException;
import cmahy.webapp.resource.impl.application.stream.service.zip.ZipEntryProxy;

import java.io.IOException;

public interface ZipSingleEntryTaskExecutor {

    void execute(ZipEntryProxy zipEntryProxy) throws EmptyZipEntryException, IOException;
}
