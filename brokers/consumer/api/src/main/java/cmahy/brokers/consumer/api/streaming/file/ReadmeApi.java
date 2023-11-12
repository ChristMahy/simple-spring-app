package cmahy.brokers.consumer.api.streaming.file;

import cmahy.brokers.consumer.api.UriConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Optional;

@RequestMapping(path = UriConstant.Readme.BASE)
public interface ReadmeApi {

    @GetMapping
    ResponseEntity<StreamingResponseBody> streamingSomeData(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) throws IOException;
}
