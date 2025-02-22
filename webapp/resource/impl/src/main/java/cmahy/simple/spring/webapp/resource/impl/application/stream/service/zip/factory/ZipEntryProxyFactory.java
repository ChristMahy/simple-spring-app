package cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.factory;

import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.ZipProxy;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.ZipEntryProxy;
import jakarta.inject.Named;

@Named
public class ZipEntryProxyFactory {

    public ZipEntryProxy create(ZipProxy zipProxy) {
        return new ZipEntryProxy(zipProxy);
    }
}
