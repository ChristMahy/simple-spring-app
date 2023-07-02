package cmahy.springapp.resourceserver.controller.rest;

import cmahy.springapp.resourceserver.service.integration.FileWriterGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static cmahy.springapp.resourceserver.controller.rest.ApiUrlConstant.IntegrationUrl.BASIC_INTEGRATION_URL;

@RestController
@RequestMapping(path = BASIC_INTEGRATION_URL)
public class IntegrationController {

    private final FileWriterGateway fileWriter;

    public IntegrationController(FileWriterGateway fileWriter) {
        this.fileWriter = fileWriter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void publishMessage(@RequestBody String message) {
        this.fileWriter.writeToFile(
            "test", message
        );
    }
}
