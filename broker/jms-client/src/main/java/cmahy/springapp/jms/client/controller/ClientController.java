package cmahy.springapp.jms.client.controller;

import cmahy.springapp.jms.client.controller.vo.output.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @GetMapping
    public Message getMessage() {
        return new Message("Test it");
    }
}
