package cmahy.webapp.resource.impl.adapter.api.stream.zip;

import cmahy.webapp.resource.api.stream.zip.ZipApi;
import cmahy.webapp.resource.impl.adapter.api.stream.factory.GeneratorOptionsFactory;
import cmahy.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorFactory;
import cmahy.webapp.resource.impl.application.stream.query.zip.GenerateZipWithRandomContentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ZipApiImpl implements ZipApi {

    private final GenerateZipWithRandomContentQuery query;
    private final GeneratorOptionsFactory optionsFactory;
    private final StreamVisitorFactory streamVisitorFactory;

    public ZipApiImpl(
        GenerateZipWithRandomContentQuery query,
        GeneratorOptionsFactory optionsFactory,
        StreamVisitorFactory streamVisitorFactory
    ) {
        this.query = query;
        this.optionsFactory = optionsFactory;
        this.streamVisitorFactory = streamVisitorFactory;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> makeAZip(Optional<Boolean> onFailure) throws IOException {
        var streamVisitor = streamVisitorFactory.create();

        streamVisitor.contentType("application/zip");

        return query.execute(
            streamVisitor,
            optionsFactory.zip(onFailure.orElse(Boolean.FALSE))
        );
    }
}
