package cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.executor;

import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.ZipProxy;

import java.io.IOException;

public interface ZipTaskExecutor {

    void execute(ZipProxy zipProxy) throws IOException;
}
