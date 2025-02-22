package cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.visitor;

import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamVisitor;
import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import org.springframework.http.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.time.Duration;
import java.util.Optional;

public class StreamVisitorImpl implements StreamVisitor<String, StreamConsumer, ResponseEntity<StreamingResponseBody>> {

    private Optional<String> fileName = Optional.empty();
    private Optional<String> contentType = Optional.empty();

    public void contentType(String contentType) {
        this.contentType = Optional.ofNullable(contentType);
    }

    @Override
    public void prepare(String prepare) {
        this.fileName = Optional.ofNullable(prepare);
    }

    @Override
    public ResponseEntity<StreamingResponseBody> build(StreamConsumer body) {
        var builder = ResponseEntity.ok();

        builder
            .cacheControl(
                CacheControl
                    .maxAge(Duration.ZERO)
                    .mustRevalidate()
            )
            .header(HttpHeaders.PRAGMA, "no-cache");

        fileName.ifPresent(name -> builder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + name));

        builder.header(
            HttpHeaders.CONTENT_TYPE,
            contentType.orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE)
        );

        return builder.body(body::writeTo);
    }
}
