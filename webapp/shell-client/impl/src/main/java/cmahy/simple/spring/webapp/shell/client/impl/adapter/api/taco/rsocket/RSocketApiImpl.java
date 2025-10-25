package cmahy.simple.spring.webapp.shell.client.impl.adapter.api.taco.rsocket;

import cmahy.simple.spring.webapp.shell.client.api.taco.rsocket.RSocketApi;
import cmahy.simple.spring.webapp.shell.client.impl.application.query.PrintMessageQuery;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.ConsolePropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.repository.property.RSocketPropertyRepository;
import cmahy.simple.spring.webapp.shell.client.impl.application.vo.property.rsocket.RSocketPropertyVo;
import jakarta.inject.Named;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;
import picocli.CommandLine;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

@Named
public class RSocketApiImpl extends RSocketApi {

    private static final Logger LOG = LoggerFactory.getLogger(RSocketApiImpl.class);

    private final PrintMessageQuery printMessageQuery;
    private final ConsolePropertyRepository consolePropertyRepository;
    private final RSocketRequester.Builder builder;
    private final RSocketPropertyRepository rSocketPropertyRepository;
    private final Random random;

    public RSocketApiImpl(
        PrintMessageQuery printMessageQuery,
        ConsolePropertyRepository consolePropertyRepository,
        RSocketRequester.Builder builder,
        RSocketPropertyRepository rSocketPropertyRepository
    ) {
        this.printMessageQuery = printMessageQuery;
        this.consolePropertyRepository = consolePropertyRepository;
        this.builder = builder;
        this.rSocketPropertyRepository = rSocketPropertyRepository;

        this.random = new Random();
    }

    @Override
    public Integer call() throws Exception {

        try {
            LOG.info("RSocket menu started.");

            try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8)
            ) {
                CommandLine.usage(this, ps);

                printMessageQuery.execute(String.format(
                    consolePropertyRepository
                        .findFormat()
                        .orElse("%s"),
                    baos.toString(StandardCharsets.UTF_8)
                ));
            }

            LOG.info("RSocket menu has been finished successfully.");

            return 0;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }

    }

    @Override
    public Integer requestResponse() {

        try {

            RSocketPropertyVo rSocketPropertyVo = rSocketPropertyRepository.rSocketProperty().orElseThrow(IllegalArgumentException::new);

            Integer length = rSocketPropertyVo.randomizer().stringLength();

            LOG.info("RSocket menu started, get a randomized string with a length of <{}>.", length);

            RSocketRequester requester = createRequester(rSocketPropertyVo);

            String response = requester
                .route("r-socket/request-response/{size}", length)
                .retrieveMono(String.class)
                .block();

            LOG.info("Got a response <{}>", response);

            LOG.info("RSocket menu has been finished successfully.");

            return 0;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }

    }


    @Override
    public Integer requestStream() {

        try {

            RSocketPropertyVo rSocketPropertyVo = rSocketPropertyRepository.rSocketProperty().orElseThrow(IllegalArgumentException::new);

            Integer length = rSocketPropertyVo.randomizer().stringLength();

            LOG.info("RSocket menu started, get a randomized string with a length of <{}>.", length);

            RSocketRequester requester = createRequester(rSocketPropertyVo);

            List<String> response = requester
                .route("r-socket/request-stream/{stream-length}/{string-size}", 50, length)
                .retrieveFlux(String.class)
                .doOnNext(r -> LOG.info("Got a response <{}>", r))
                .collectList()
                .block();

            LOG.info("Got a response <{}>", Objects.nonNull(response) ? String.join(";\n\r", response) : "");

            LOG.info("RSocket menu has been finished successfully.");

            return 0;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }

    }

    @Override
    public Integer fireAndForget() {

        try {

            RSocketPropertyVo rSocketPropertyVo = rSocketPropertyRepository.rSocketProperty().orElseThrow(IllegalArgumentException::new);

            LOG.info("RSocket menu started, fire and forget");

            RSocketRequester requester = createRequester(rSocketPropertyVo);

            requester
                .route("r-socket/fire-and-forget")
                .data(random.nextInt(100_000))
                .send()
                .block();

            LOG.info("RSocket menu fire and forget has been finished successfully.");

            return 0;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }

    }

    @Override
    public Integer channel() {

        try {

            Flux<ChannelOutputVo> channelOutputs = Flux
                .fromStream(
                    Stream
                        .generate(() -> new ChannelOutputVo(
                            random.nextInt(1, 100),
                            random.nextInt(1, 10_000)
                        ))
                        .limit(random.nextInt(1, 50))
                )
                .delayElements(Duration.ofSeconds(1));

            RSocketPropertyVo rSocketPropertyVo = rSocketPropertyRepository.rSocketProperty().orElseThrow(IllegalArgumentException::new);

            LOG.info("RSocket menu started, channel");

            RSocketRequester requester = createRequester(rSocketPropertyVo);

            requester
                .route("r-socket/channel")
                .data(channelOutputs.doOnNext(channelOut -> LOG.info("Sending request <{}>", channelOut)))
                .retrieveFlux(new ParameterizedTypeReference<Collection<String>>() {})
                .doOnNext(stringsIn -> LOG.info("Got a response <\n\r{}\n\r>", String.join(";\n\r", stringsIn)))
                .then()
                .block();

            LOG.info("RSocket menu channel has been finished successfully.");

            return 0;

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            return 1;
        }

    }

    private RSocketRequester createRequester(RSocketPropertyVo rSocketPropertyVo) {

        if (Objects.nonNull(rSocketPropertyVo.host()) && Objects.nonNull(rSocketPropertyVo.port())) {

            return builder.tcp(
                rSocketPropertyVo.host(),
                rSocketPropertyVo.port()
            );

        }

        if (Objects.nonNull(rSocketPropertyVo.uri())) {

            return builder.websocket(rSocketPropertyVo.uri());

        }

        throw new IllegalArgumentException("RSocket or WebSocket property is required.");

    }

    private record ChannelOutputVo(
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
