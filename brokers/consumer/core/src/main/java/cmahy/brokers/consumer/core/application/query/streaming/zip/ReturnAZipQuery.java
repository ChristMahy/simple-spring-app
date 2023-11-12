package cmahy.brokers.consumer.core.application.query.streaming.zip;

import cmahy.common.annotation.Query;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Query
@Named
public class ReturnAZipQuery {

    private static final Logger LOG = LoggerFactory.getLogger(ReturnAZipQuery.class);

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";
    private static final int LETTERS_LENGTH = LETTERS.length();
    private static final Random RANDOM = new Random();

    public Consumer<OutputStream> execute(Boolean onFailure) {
        LOG.info("Start zip process");

        List<Consumer<ZipOutputStream>> consumers = IntStream.range(1, 1024)
            .mapToObj(index -> {
                LOG.info("Add entry <{}>", index);

                if (Boolean.TRUE.equals(onFailure) && index >= 130) {
                    throw new RuntimeException("Zip exception run");
                }

                return (Consumer<ZipOutputStream>) (zipStream) -> {
                    try {
                        LOG.info("Generate entry <{}>", index);

                        final String aString = generateAString(5 * 1024 * 1024);

                        zipStream.putNextEntry(new ZipEntry("readme_" + index + ".md"));

                        LOG.info("Write entry <{}>", index);

                        zipStream.write(
                            ("Test readme with some text <" + index + ">, " + aString).getBytes()
                        );

                        LOG.info("Close entry <{}>", index);

                        zipStream.closeEntry();
                    } catch (IOException ioException) {
                        throw new RuntimeException(ioException);
                    }
                };
            })
            .toList();

        return (outputStream) -> {
            try (var bufferedOutputStream = new BufferedOutputStream(outputStream)) {
                try (var zipOutputStream = new ZipOutputStream(bufferedOutputStream)) {
                    consumers.forEach(c -> c.accept(zipOutputStream));

                    zipOutputStream.finish();
                    zipOutputStream.flush();
                }
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        };
    }

    public String generateAString(int length) {
        final var stringBuilder = new StringBuilder(length);

        while (length-- > 0) {
            stringBuilder.append(
                LETTERS.charAt(RANDOM.nextInt(0, LETTERS_LENGTH - 1))
            );
        }

        return stringBuilder.toString();
    }
}
