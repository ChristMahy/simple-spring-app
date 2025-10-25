package cmahy.simple.spring.webapp.resource.impl.adapter.api.rsocket;

import cmahy.simple.spring.webapp.resource.impl.application.service.StringGeneratorService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
public class RandomizerRSocket {

    private static final Logger LOG = LoggerFactory.getLogger(RandomizerRSocket.class);

    private final StringGeneratorService stringGeneratorService;
    private final RSocketRequester.Builder builder;

    public RandomizerRSocket(
        StringGeneratorService stringGeneratorService,
        RSocketRequester.Builder builder
    ) {
        this.stringGeneratorService = stringGeneratorService;
        this.builder = builder;
    }

    @MessageMapping("r-socket/request-response/{size}")
    public Mono<String> requestResponse(@DestinationVariable("size") Integer stringSize) {
        return Mono.just(stringGeneratorService.execute(stringSize))
            .doOnNext(string -> {
                LOG.info("<{}> string length request, <{}>", stringSize, string);
            });
    }

    @MessageMapping("r-socket/request-stream/{stream-length}/{string-size}")
    public Flux<String> requestStream(
        @DestinationVariable("stream-length") Integer streamLength,
        @DestinationVariable("string-size") Integer stringSize
    ) {
        return Flux
            .interval(Duration.ofSeconds(1))
            .take(streamLength)
            .map(_ -> stringGeneratorService.execute(stringSize));
    }



    @MessageMapping("r-socket/fire-and-forget")
    public Mono<Void> fireAndForget(Mono<Integer> randomNumber) {
        return randomNumber
            .doOnNext(rN -> LOG.info("Got a random number <{}>", rN))
            .thenEmpty(Mono.empty());
    }



    @MessageMapping("r-socket/channel")
    public Flux<Collection<String>> channel(Flux<ChannelInputVo> channelInput) {
        return channelInput
            .doOnNext(channelIn -> LOG.info("Got <{}> as channel input", channelIn))
            .map(channelIn ->
                IntStream.rangeClosed(1, channelIn.streamLength())
                    .mapToObj(_ -> stringGeneratorService.execute(channelIn.stringSize()))
                    .toList()
            );
    }

    public record ChannelInputVo(
        Integer streamLength,
        Integer stringSize
    ) {
        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("streamLength", streamLength())
                .append("stringSize", stringSize())
                .toString();
        }
    }

}
