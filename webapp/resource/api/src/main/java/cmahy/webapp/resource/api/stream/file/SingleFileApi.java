package cmahy.webapp.resource.api.stream.file;

import cmahy.webapp.resource.api.stream.StreamUriConstant.SingleFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RequestMapping(path = SingleFile.BASE)
public interface SingleFileApi {

    @GetMapping(path = SingleFile.README)
    ResponseEntity<StreamingResponseBody> readme(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) throws IOException;
}
