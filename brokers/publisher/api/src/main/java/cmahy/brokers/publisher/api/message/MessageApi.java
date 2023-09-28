package cmahy.brokers.publisher.api.message;

import cmahy.brokers.publisher.api.UriConstant;
import cmahy.brokers.publisher.api.vo.output.MessageOutputApiVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = UriConstant.BASE + UriConstant.Message.BASE)
public interface MessageApi {

    @GetMapping
    Iterable<MessageOutputApiVo> allMessages();
}
