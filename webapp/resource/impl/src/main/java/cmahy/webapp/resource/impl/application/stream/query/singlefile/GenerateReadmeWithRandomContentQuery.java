package cmahy.webapp.resource.impl.application.stream.query.singlefile;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.service.StringGeneratorService;
import cmahy.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import cmahy.webapp.resource.impl.application.stream.visitor.StreamVisitor;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

@Query
@Named
public class GenerateReadmeWithRandomContentQuery {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateReadmeWithRandomContentQuery.class);

    private final StringGeneratorService stringGenerator;
    private final Random random;

    public GenerateReadmeWithRandomContentQuery(
        StringGeneratorService stringGenerator,
        Random random
    ) {
        this.stringGenerator = stringGenerator;
        this.random = random;
    }

    public <R> R execute(StreamVisitor<String, StreamConsumer, R> streamVisitor, Boolean onFailure) throws IOException {
        streamVisitor.prepare("readme.md");

        List<Consumer<OutputStream>> consumers = new ArrayList<>(1024);

        for (int i = 0; i < 1024; i++) {
            final var finalI = i;

            consumers.add((outStream) -> {
                try {
                    LOG.info("Write a new line <{}>", finalI);

                    String aString = stringGenerator.execute(5 * 1024);

                    outStream.write(("Line <" + (finalI + 1) + ">, " + aString + "\r\n\r\n\r\n").getBytes());
                    outStream.flush();
                } catch (IOException ioE) {
                    throw new UncheckedIOException(ioE);
                }
            });
        }

        return streamVisitor.build(outputStream -> {
            int failAtPosition = random.nextInt(0, consumers.size() - 1);

            for (int i = 0; i < consumers.size(); i++) {
                if (Boolean.TRUE.equals(onFailure) && i >= failAtPosition) {
                    throw new IOException("Ho an exception...");
                }

                consumers.get(i).accept(outputStream);
            }
        });
    }
}
