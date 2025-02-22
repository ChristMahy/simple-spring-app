package cmahy.simple.spring.webapp.resource.api.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static cmahy.simple.spring.webapp.resource.api.UriConstant.BASE_V1;

@RequestMapping(path = LoggerApi.BASE_URL)
public interface LoggerApi {

    String BASE_URL = BASE_V1 + "/log";

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void logSomeText(@RequestBody String line);
}
