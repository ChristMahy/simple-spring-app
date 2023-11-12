package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.service.ReadmeGeneratorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
@RequestMapping(path = "/readme-generator")
public class ReadmeGeneratorController {

    private final ReadmeGeneratorService readmeGeneratorService;

    public ReadmeGeneratorController(ReadmeGeneratorService readmeGeneratorService) {
        this.readmeGeneratorService = readmeGeneratorService;
    }

    @GetMapping
    public ResponseEntity<Flux<byte[]>> download(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"a-readme.md\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(readmeGeneratorService.execute(onFailure.orElse(Boolean.FALSE)));
    }
}
