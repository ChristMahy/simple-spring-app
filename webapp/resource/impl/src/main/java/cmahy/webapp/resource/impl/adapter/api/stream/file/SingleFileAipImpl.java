package cmahy.webapp.resource.impl.adapter.api.stream.file;

import cmahy.webapp.resource.api.stream.file.SingleFileApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
public class SingleFileAipImpl implements SingleFileApi {

    @Override
    public ResponseEntity<StreamingResponseBody> readme(Optional<Boolean> onFailure) throws IOException {
        throw new IllegalStateException("Not yet implemented !");
    }
}
