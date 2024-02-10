package cmahy.webapp.resource.impl.application.stream.service.zip.factory;

import cmahy.webapp.resource.impl.application.stream.service.zip.ZipProxy;
import cmahy.webapp.resource.impl.application.stream.service.zip.ZipEntryProxy;
import jakarta.inject.Named;

import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

@Named
public class ZipEntryProxyFactory {

    public ZipEntryProxy create(ZipProxy zipProxy) {
        return new ZipEntryProxy(zipProxy);
    }
}
