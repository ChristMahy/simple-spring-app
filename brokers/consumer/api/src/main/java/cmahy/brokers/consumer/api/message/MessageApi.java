package cmahy.brokers.consumer.api.message;

import cmahy.brokers.consumer.api.UriConstant;
import cmahy.brokers.consumer.api.vo.output.MessageOutputApiVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = UriConstant.Message.BASE)
public interface MessageApi {

    @GetMapping
    ResponseEntity<Iterable<MessageOutputApiVo>> allMessages();
}
