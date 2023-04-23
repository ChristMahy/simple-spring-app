package cmahy.springapp.rabbitmq.resource.controller;

import cmahy.springapp.rabbitmq.resource.controller.vo.input.MessageInputVO;
import cmahy.springapp.rabbitmq.resource.controller.vo.output.MessageOutputVO;
import cmahy.springapp.rabbitmq.resource.domain.Message;
import cmahy.springapp.rabbitmq.resource.service.RabbitMQService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/resource", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

    private final RabbitMQService rabbitMQService;

    public ResourceController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @GetMapping
    public MessageOutputVO getMessage() {
        return new MessageOutputVO("Test it");
    }

    @PostMapping
    public MessageOutputVO postMessage(@RequestBody MessageInputVO inputMessage) {
        final Message message = new Message(inputMessage.message());

        rabbitMQService.sendMessage(message);

        return new MessageOutputVO(message.message());
    }
}
