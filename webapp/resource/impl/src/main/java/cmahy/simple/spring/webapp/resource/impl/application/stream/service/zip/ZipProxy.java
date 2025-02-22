package cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip;

import java.io.*;
import java.util.zip.ZipOutputStream;

public class ZipProxy implements Closeable {

    private final ZipOutputStream zipOutputStream;

    public ZipProxy(OutputStream outputStream) {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 576);
        this.zipOutputStream = new ZipOutputStream(bufferedOutputStream);
    }

    public ZipOutputStream outputStream() {
        return this.zipOutputStream;
    }

    @Override
    public void close() throws IOException {
        this.zipOutputStream.finish();

        this.zipOutputStream.flush();

        this.zipOutputStream.close();
    }
}
