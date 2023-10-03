package cmahy.brokers.publisher.api.message;

import cmahy.brokers.publisher.api.UriConstant;
import cmahy.brokers.publisher.api.vo.id.MessageApiId;
import cmahy.brokers.publisher.api.vo.input.MessageInputApiVo;
import cmahy.brokers.publisher.api.vo.output.MessageOutputApiVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = UriConstant.BASE + UriConstant.Message.BASE)
public interface MessageApi {

    @GetMapping
    Iterable<MessageOutputApiVo> allMessages();

    @PostMapping
    MessageOutputApiVo create(@RequestBody MessageInputApiVo input);

    @PutMapping(path = "/{id}")
    MessageOutputApiVo update(@RequestBody MessageInputApiVo input, @PathVariable MessageApiId id);

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable MessageApiId id);
}
