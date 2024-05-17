package cmahy.webapp.resource.api.stream.file;

import cmahy.webapp.resource.api.UriConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RequestMapping(path = SingleFileApi.BASE)
public interface SingleFileApi {

    String BASE = UriConstant.BASE_V1 + "/single-file";

    String README = "/readme";

    @GetMapping(path = README)
    ResponseEntity<StreamingResponseBody> readme(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) throws IOException;
}
