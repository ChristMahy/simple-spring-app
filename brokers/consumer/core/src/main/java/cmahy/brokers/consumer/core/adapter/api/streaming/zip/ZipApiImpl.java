package cmahy.brokers.consumer.core.adapter.api.streaming.zip;

import cmahy.brokers.consumer.api.streaming.zip.ZipApi;
import cmahy.brokers.consumer.core.application.query.streaming.zip.ReturnAZipQuery;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;

@RestController
public class ZipApiImpl implements ZipApi {

    private final ReturnAZipQuery query;

    public ZipApiImpl(ReturnAZipQuery query) {
        this.query = query;
    }

    @Override
    public ResponseEntity<StreamingResponseBody> makeAZip(Optional<Boolean> onFailure) {

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sample.zip")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
            .body(query.execute(onFailure.orElse(Boolean.FALSE))::accept);
    }
}
