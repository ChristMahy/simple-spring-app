package cmahy.brokers.consumer.api.streaming.zip;

import cmahy.brokers.consumer.api.UriConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;

@RequestMapping(path = UriConstant.Zip.BASE)
public interface ZipApi {

    @GetMapping
    ResponseEntity<StreamingResponseBody> makeAZip(
        @RequestParam(required = false) Optional<Boolean> onFailure
    );
}
