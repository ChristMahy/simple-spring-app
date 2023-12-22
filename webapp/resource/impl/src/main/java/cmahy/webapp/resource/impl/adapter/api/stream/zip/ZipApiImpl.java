package cmahy.webapp.resource.impl.adapter.api.stream.zip;

import cmahy.webapp.resource.api.stream.zip.ZipApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ZipApiImpl implements ZipApi {

    @Override
    public ResponseEntity<StreamingResponseBody> makeAZip(Optional<Boolean> onFailure) throws IOException {
        throw new IllegalStateException("Not yet implemented !");
    }
}
