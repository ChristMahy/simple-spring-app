package cmahy.brokers.consumer.message.api;

import cmahy.brokers.consumer.message.api.MessageUriConstant;
import cmahy.brokers.consumer.message.api.vo.output.MessageOutputApiVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = MessageUriConstant.Message.BASE)
public interface MessageApi {

    @GetMapping
    ResponseEntity<Iterable<MessageOutputApiVo>> allMessages();
}
