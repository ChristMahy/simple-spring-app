package cmahy.brokers.consumer.core.adapter.api.streaming.readme;

import cmahy.brokers.consumer.api.streaming.file.ReadmeApi;
import cmahy.brokers.consumer.core.application.query.streaming.readme.ReturnAReadmeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ReadmeApiImpl implements ReadmeApi {

    private static final Logger LOG = LoggerFactory.getLogger(ReadmeApiImpl.class);

    private final ReturnAReadmeQuery query;

    public ReadmeApiImpl(ReturnAReadmeQuery query) {
        this.query = query;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> streamingSomeData(Optional<Boolean> onFailure) throws IOException {
        LOG.info("Build http response");

        var responseEntity = ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=readme.md")
            .header(HttpHeaders.CONTENT_TYPE, "text/markdown")
            .body((StreamingResponseBody) query.execute(onFailure.orElse(Boolean.FALSE))::accept);

        LOG.info("Return http response");

        return responseEntity;
    }
}
