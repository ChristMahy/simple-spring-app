package cmahy.springapp.jms.resource.controller;

import cmahy.springapp.jms.resource.controller.vo.input.MessageInputVO;
import cmahy.springapp.jms.resource.controller.vo.output.MessageOutputVO;
import cmahy.springapp.jms.resource.domain.Message;
import cmahy.springapp.jms.resource.service.JmsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/resource", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

    private final JmsService jmsService;

    public ResourceController(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @GetMapping
    public MessageOutputVO getMessage() {
        return new MessageOutputVO("Test it");
    }

    @PostMapping
    public MessageOutputVO postMessage(@RequestBody MessageInputVO inputMessage) {
        final Message message = new Message(inputMessage.message());

        jmsService.sendMessage(message);

        return new MessageOutputVO(message.message());
    }
}
