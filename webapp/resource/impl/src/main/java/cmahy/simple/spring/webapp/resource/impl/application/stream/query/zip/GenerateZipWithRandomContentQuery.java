package cmahy.simple.spring.webapp.resource.impl.application.stream.query.zip;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.*;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.*;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.executor.ZipSingleEntryTaskExecutor;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.factory.ZipEntryProxyFactory;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.factory.ZipProxyFactory;
import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamVisitor;
import cmahy.simple.spring.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Query
@Named
public class GenerateZipWithRandomContentQuery {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateZipWithRandomContentQuery.class);

    private final DateTimeFormatter yearMonthDayHoursMinutesSeconds;
    private final ZipRandomContentWriterTasksFactory tasksFactory;
    private final ZipProxyFactory zipProxyFactory;
    private final ZipEntryProxyFactory zipEntryProxyFactory;

    public GenerateZipWithRandomContentQuery(
        DateTimeFormatter yearMonthDayHoursMinutesSeconds,
        ZipRandomContentWriterTasksFactory tasksFactory,
        ZipProxyFactory zipProxyFactory,
        ZipEntryProxyFactory zipEntryProxyFactory
    ) {
        this.yearMonthDayHoursMinutesSeconds = yearMonthDayHoursMinutesSeconds;
        this.tasksFactory = tasksFactory;
        this.zipProxyFactory = zipProxyFactory;
        this.zipEntryProxyFactory = zipEntryProxyFactory;
    }

    public <R> R execute(StreamVisitor<String, StreamConsumer, R> streamVisitor, GeneratorQueryOption options) throws IOException {
        LOG.info("Start zip process");

        streamVisitor.prepare("a-name-for-a-zip-" + LocalDateTime.now().format(yearMonthDayHoursMinutesSeconds) + ".zip");

        List<ZipSingleEntryTaskExecutor> zipSingleEntryTasks = tasksFactory.execute(options);

        LOG.info("<{}> tasks ready to generate entry", zipSingleEntryTasks.size());

        return streamVisitor.build(outputStream -> {
            int folderName = 0;
            try (ZipProxy zip = zipProxyFactory.create(outputStream)) {
                for (var cpt = 0; cpt < zipSingleEntryTasks.size(); cpt++) {
                    if (cpt % 10 == 0) {
                        folderName++;
                    }

                    try (ZipEntryProxy zipEntry = zipEntryProxyFactory.create(zip)) {
                        zipEntry.initializeEntry(Path.of(String.valueOf(folderName), "readme_" + cpt + ".md").toString());

                        zipSingleEntryTasks.get(cpt).execute(zipEntry);
                    }
                }
            }
        });
    }
}
