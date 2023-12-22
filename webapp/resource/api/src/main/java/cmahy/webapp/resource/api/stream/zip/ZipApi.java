package cmahy.webapp.resource.api.stream.zip;

import cmahy.webapp.resource.api.stream.StreamUriConstant.Zip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RequestMapping(path = Zip.BASE)
public interface ZipApi {

    ResponseEntity<StreamingResponseBody> makeAZip(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) throws IOException;
}
