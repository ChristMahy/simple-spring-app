package cmahy.simple.spring.webapp.resource.impl.application.stream.query.singlefile;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.singlefile.SingleReadmeRandomContentWriterTaskFactory;
import cmahy.simple.spring.webapp.resource.impl.application.stream.service.singlefile.executor.SingleFileReadmeTaskExecutor;
import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamVisitor;
import cmahy.simple.spring.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Query
@Named
public class GenerateReadmeWithRandomContentQuery {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateReadmeWithRandomContentQuery.class);

    private final SingleReadmeRandomContentWriterTaskFactory taskGenerator;

    public GenerateReadmeWithRandomContentQuery(
        SingleReadmeRandomContentWriterTaskFactory taskGenerator
    ) {
        this.taskGenerator = taskGenerator;
    }

    public <R> R execute(StreamVisitor<String, StreamConsumer, R> streamVisitor, GeneratorQueryOption options) throws IOException {
        LOG.info("Start single file write process");

        streamVisitor.prepare("readme.md");

        List<SingleFileReadmeTaskExecutor> tasks = taskGenerator.execute(options);

        LOG.info("<{}> tasks ready to generate content", tasks.size());

        return streamVisitor.build(outputStream -> {
            for (var task : tasks) {
                task.execute(outputStream);
            }
        });
    }
}
