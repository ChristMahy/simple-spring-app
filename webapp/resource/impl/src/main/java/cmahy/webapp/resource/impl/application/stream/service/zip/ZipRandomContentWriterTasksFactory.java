package cmahy.webapp.resource.impl.application.stream.service.zip;

import cmahy.webapp.resource.impl.application.service.StringGeneratorService;
import cmahy.webapp.resource.impl.application.stream.exception.FailAtPositionException;
import cmahy.webapp.resource.impl.application.stream.service.zip.executor.ZipSingleEntryTaskExecutor;
import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Named
public class ZipRandomContentWriterTasksFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ZipRandomContentWriterTasksFactory.class);

    private final StringGeneratorService stringGeneratorService;
    private final Random randomizer;

    public ZipRandomContentWriterTasksFactory(StringGeneratorService stringGeneratorService, Random randomizer) {
        this.stringGeneratorService = stringGeneratorService;
        this.randomizer = randomizer;
    }

    public List<ZipSingleEntryTaskExecutor> execute(GeneratorQueryOption options) {
        var elementsSizeOrDefault = options.elementsSizeOrDefaultAboveZero();
        var failAtPosition = randomizer.nextInt(0, elementsSizeOrDefault);

        return IntStream.rangeClosed(1, elementsSizeOrDefault)
            .mapToObj(index -> {
                LOG.info("Add entry <{}>", index);

                return (ZipSingleEntryTaskExecutor) (zipStream) -> {
                    LOG.info("Generate entry <{}>", index);

                    final String aString = stringGeneratorService.execute(
                        options.stringSizeOrDefaultAboveZero(5 * 1024 * 1024)
                    );

                    LOG.info("Write entry <{}>", index);

                    zipStream.appendToEntryContent(("Test readme with some text <" + index + ">, " + aString).getBytes());

                    LOG.info("Close entry <{}>", index);

                    if (Boolean.TRUE.equals(options.onFailure()) && index >= failAtPosition) {
                        throw new FailAtPositionException("Zip exception run");
                    }
                };
            })
            .toList();
    }
}
