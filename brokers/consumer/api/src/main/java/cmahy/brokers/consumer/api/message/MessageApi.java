package cmahy.brokers.consumer.api.message;

import cmahy.brokers.consumer.api.UriConstant;
import cmahy.brokers.consumer.api.vo.output.MessageOutputApiVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = UriConstant.BASE + UriConstant.Message.BASE)
public interface MessageApi {

    @GetMapping
    Iterable<MessageOutputApiVo> allMessages();
}
