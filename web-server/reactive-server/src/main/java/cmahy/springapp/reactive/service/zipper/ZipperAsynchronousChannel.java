package cmahy.springapp.reactive.service.zipper;

import cmahy.springapp.reactive.service.StringGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipperAsynchronousChannel implements AsynchronousChannel {

    private static final Logger LOG = LoggerFactory.getLogger(ZipperAsynchronousChannel.class);

    private final ExecutorService executor;
    private final StringGeneratorService stringGeneratorService;

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final ZipOutputStream zipOutputStream;

    private volatile boolean closed = false;

    public ZipperAsynchronousChannel(StringGeneratorService stringGeneratorService) {
        this.executor = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r);

            t.setDaemon(true);

            return t;
        });
        this.stringGeneratorService = stringGeneratorService;

        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.zipOutputStream = new ZipOutputStream(this.byteArrayOutputStream);
    }

    public void read(
        Consumer<byte[]> dst,
        Integer position,
        ZipperAttachment attachment,
        ZipperCompletionHandler handler
    ) {
        if (!isOpen()) {
            Throwable exception = new ClosedChannelException();

            executor.execute(() -> {
                handler.failed(exception, attachment);
            });

            return;
        }

        Runnable task = () -> {
            try {
                LOG.info("Generate entry <{}>", position);

                final String aString = stringGeneratorService.generateAString(5 * 1024 * 1024);

                zipOutputStream.putNextEntry(new ZipEntry("readme_" + position + ".md"));

                LOG.info("Write entry <{}>", position);

                zipOutputStream.write(
                    ("Test readme with some text <" + position + ">, " + aString).getBytes()
                );

                LOG.info("Close entry <{}>", position);

                zipOutputStream.closeEntry();

                byte[] bytes = byteArrayOutputStream.toByteArray();

                byteArrayOutputStream.reset();

                dst.accept(bytes);

                handler.completed(position, attachment);
            } catch (IOException ioException) {
                handler.failed(ioException, attachment);
            }
        };

        executor.execute(task);
    }

    public void finish(
        Consumer<byte[]> dst,
        ZipperCompletionHandler handler
    ) {
        Runnable task = () -> {
            try {
                zipOutputStream.finish();
                zipOutputStream.flush();

                byte[] bytes = byteArrayOutputStream.toByteArray();

                byteArrayOutputStream.reset();

                dst.accept(bytes);
            } catch (IOException ioException) {
                handler.failed(ioException, null);
            }
        };

        executor.execute(task);
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }

        closed = true;

        zipOutputStream.close();
    }

    @Override
    public boolean isOpen() {
        return !closed;
    }
}
