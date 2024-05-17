package cmahy.webapp.resource.api.stream.zip;

import cmahy.webapp.resource.api.UriConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RequestMapping(path = ZipApi.BASE)
public interface ZipApi {

    String BASE = UriConstant.BASE_V1 + "/zip";

    @GetMapping
    ResponseEntity<StreamingResponseBody> makeAZip(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) throws IOException;
}
