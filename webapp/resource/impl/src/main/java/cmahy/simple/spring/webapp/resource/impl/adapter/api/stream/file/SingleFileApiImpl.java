package cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.file;

import cmahy.simple.spring.webapp.resource.api.stream.file.SingleFileApi;
import cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.factory.GeneratorOptionsFactory;
import cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorFactory;
import cmahy.simple.spring.webapp.resource.impl.application.stream.query.singlefile.GenerateReadmeWithRandomContentQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
public class SingleFileApiImpl implements SingleFileApi {

    private static final Logger LOG = LoggerFactory.getLogger(SingleFileApiImpl.class);

    private final GenerateReadmeWithRandomContentQuery query;
    private final GeneratorOptionsFactory optionsFactory;
    private final StreamVisitorFactory streamVisitorFactory;

    public SingleFileApiImpl(
        GenerateReadmeWithRandomContentQuery query,
        GeneratorOptionsFactory optionsFactory,
        StreamVisitorFactory streamVisitorFactory
    ) {
        this.query = query;
        this.optionsFactory = optionsFactory;
        this.streamVisitorFactory = streamVisitorFactory;
    }

    @Override
    @PreAuthorize("hasAuthority(@preAuthorizeScope.GUEST)")
    public ResponseEntity<StreamingResponseBody> readme(Optional<Boolean> onFailure) throws IOException {

        LOG.info("Build http response");

        var responseVisitor = streamVisitorFactory.create();

        responseVisitor.contentType(MediaType.TEXT_MARKDOWN_VALUE);

        LOG.info("Return http response");

        return query.execute(
            responseVisitor,
            this.optionsFactory.singleFile(onFailure.orElse(Boolean.FALSE))
        );
    }
}
