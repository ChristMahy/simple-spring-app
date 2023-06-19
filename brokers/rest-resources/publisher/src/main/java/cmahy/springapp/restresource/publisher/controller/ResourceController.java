package cmahy.springapp.restresource.publisher.controller;

import cmahy.springapp.restresource.publisher.controller.vo.input.MessageInputVO;
import cmahy.springapp.restresource.publisher.controller.vo.output.MessageOutputVO;
import cmahy.springapp.restresource.publisher.domain.Message;
import cmahy.springapp.restresource.publisher.service.SenderMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/resource", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

    private final SenderMessage senderMessage;

    public ResourceController(SenderMessage senderMessage) {
        this.senderMessage = senderMessage;
    }

    @GetMapping
    public MessageOutputVO getMessage() {
        return new MessageOutputVO("Test it");
    }

    @PostMapping
    public MessageOutputVO postMessage(@RequestBody MessageInputVO inputMessage) {
        final Message message = new Message(inputMessage.message());

        senderMessage.sendMessage(message);

        return new MessageOutputVO(message.message());
    }
}
