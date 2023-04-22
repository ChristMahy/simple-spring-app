package cmahy.springapp.rabbitmq.client.controller;

import cmahy.springapp.rabbitmq.client.controller.vo.output.MessageOutputVO;
import cmahy.springapp.rabbitmq.client.service.MessageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final MessageService messageService;

    public ClientController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<MessageOutputVO> getMessages() {
        return messageService.getMessages();
    }
}
