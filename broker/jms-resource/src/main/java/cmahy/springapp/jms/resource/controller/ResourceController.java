package cmahy.springapp.jms.resource.controller;

import cmahy.springapp.jms.resource.controller.vo.output.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/resource", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

    @GetMapping
    public Message getMessage() {
        return new Message("Test it");
    }
}
