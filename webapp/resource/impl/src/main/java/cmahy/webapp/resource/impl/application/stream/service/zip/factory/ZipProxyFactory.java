package cmahy.webapp.resource.impl.application.stream.service.zip.factory;

import cmahy.webapp.resource.impl.application.stream.service.zip.ZipProxy;
import jakarta.inject.Named;

import java.io.OutputStream;

@Named
public class ZipProxyFactory {

    public ZipProxy create(OutputStream outputStream) {
        return new ZipProxy(outputStream);
    }
}
