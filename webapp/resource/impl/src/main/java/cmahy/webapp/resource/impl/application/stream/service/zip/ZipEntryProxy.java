package cmahy.webapp.resource.impl.application.stream.service.zip;

import cmahy.webapp.resource.impl.application.stream.exception.EmptyZipEntryException;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;

public class ZipEntryProxy implements Closeable {

    private final ZipProxy zipProxy;
    private Optional<ZipEntry> zipEntry = Optional.empty();

    public ZipEntryProxy(ZipProxy zipProxy) {
        this.zipProxy = zipProxy;
    }

    public Optional<ZipEntry> currentEntry() {
        return this.zipEntry;
    }

    public void initializeEntry(String name) throws IOException {
        this.zipEntry = Optional.of(new ZipEntry(name));

        zipProxy.outputStream().putNextEntry(this.zipEntry.get());
    }

    public void appendToEntryContent(byte[] bytes) throws IOException {
        if (currentEntry().isEmpty()) {
            throw new EmptyZipEntryException();
        }

        this.zipProxy.outputStream().write(bytes);
        this.zipProxy.outputStream().flush();
    }

    @Override
    public void close() throws IOException {
        currentEntry().orElseThrow(EmptyZipEntryException::new);

        zipProxy.outputStream().closeEntry();
        zipProxy.outputStream().flush();
    }
}
