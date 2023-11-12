package cmahy.brokers.consumer.core.application.query.streaming.readme;

import cmahy.common.annotation.Query;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

@Query
@Named
public class ReturnAReadmeQuery {

    private static final Logger LOG = LoggerFactory.getLogger(ReturnAReadmeQuery.class);

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ_+-=()*$%0123456789";
    private static final int LETTERS_LENGTH = LETTERS.length();
    private static final Random RANDOM = new Random();

    public Consumer<OutputStream> execute(Boolean onFailure) throws IOException {
        List<Consumer<OutputStream>> consumers = new ArrayList<>(1024);

        for (int i = 0; i < 1024; i++) {
            if (Boolean.TRUE.equals(onFailure) && i >= 20) {
                throw new IOException("Ho an exception...");
            }

            final var finalI = i;

            consumers.add((outStream) -> {
                try {
                    LOG.info("Write a new line <{}>", finalI);

                    String aString = generateAString(5 * 1024 * 1024);

                    outStream.write(("Line <" + (finalI + 1) + ">, " + aString + "\r\n\r\n\r\n").getBytes());
                } catch (IOException ioE) {
                    throw new UncheckedIOException(ioE);
                }
            });
        }

        return (outputStream) -> consumers.forEach(c -> c.accept(outputStream));
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
