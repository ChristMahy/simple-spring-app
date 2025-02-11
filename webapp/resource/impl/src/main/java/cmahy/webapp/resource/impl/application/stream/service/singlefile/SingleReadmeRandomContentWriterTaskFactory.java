package cmahy.webapp.resource.impl.application.stream.service.singlefile;

import cmahy.webapp.resource.impl.application.service.StringGeneratorService;
import cmahy.webapp.resource.impl.application.stream.exception.FailAtPositionException;
import cmahy.webapp.resource.impl.application.stream.service.singlefile.executor.SingleFileReadmeTaskExecutor;
import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Named
public class SingleReadmeRandomContentWriterTaskFactory {

    private static final Logger LOG = LoggerFactory.getLogger(SingleReadmeRandomContentWriterTaskFactory.class);

    private final StringGeneratorService stringGeneratorService;
    private final Random randomizer;

    public SingleReadmeRandomContentWriterTaskFactory(StringGeneratorService stringGeneratorService, Random randomizer) {
        this.stringGeneratorService = stringGeneratorService;
        this.randomizer = randomizer;
    }

    public List<SingleFileReadmeTaskExecutor> execute(GeneratorQueryOption options) {
        var elementsSize = options.elementsSizeOrDefaultAboveZero();
        var failAtPosition = randomizer.nextInt(1, elementsSize);

        return IntStream.rangeClosed(1, elementsSize)
            .mapToObj(index -> {
                LOG.debug("Write a new line <{}>", index);

                return (SingleFileReadmeTaskExecutor) outStream -> {
                    String aString = stringGeneratorService.execute(options.stringSize().orElse(5 * 1024));

                    outStream.write(("Line <" + (index + 1) + ">, " + aString + "\r\n\r\n\r\n").getBytes());
                    outStream.flush();

                    if (Boolean.TRUE.equals(options.onFailure()) && index >= failAtPosition) {
                        throw new FailAtPositionException("Readme exception run");
                    }
                };
            })
            .toList();
    }
}
