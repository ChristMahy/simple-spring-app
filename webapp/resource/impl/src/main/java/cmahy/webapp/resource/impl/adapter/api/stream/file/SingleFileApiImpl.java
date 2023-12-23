package cmahy.webapp.resource.impl.adapter.api.stream.file;

import cmahy.webapp.resource.api.stream.file.SingleFileApi;
import cmahy.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorImpl;
import cmahy.webapp.resource.impl.application.stream.query.singlefile.GenerateReadmeWithRandomContentQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
public class SingleFileApiImpl implements SingleFileApi {

    private static final Logger LOG = LoggerFactory.getLogger(SingleFileApiImpl.class);

    private final GenerateReadmeWithRandomContentQuery query;

    public SingleFileApiImpl(GenerateReadmeWithRandomContentQuery query) {
        this.query = query;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> readme(Optional<Boolean> onFailure) throws IOException {

        LOG.info("Build http response");

        var responseVisitor = new StreamVisitorImpl();

        responseVisitor.contentType(MediaType.TEXT_MARKDOWN_VALUE);

        LOG.info("Return http response");

        return query.execute(responseVisitor, onFailure.orElse(Boolean.FALSE));
    }
}
