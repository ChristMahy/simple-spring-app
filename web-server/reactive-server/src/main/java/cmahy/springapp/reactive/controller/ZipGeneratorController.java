package cmahy.springapp.reactive.controller;

import cmahy.springapp.reactive.service.ZipGeneratorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
@RequestMapping(path = "/zip-generator")
public class ZipGeneratorController {

    private final ZipGeneratorService zipGeneratorService;

    public ZipGeneratorController(ZipGeneratorService zipGeneratorService) {
        this.zipGeneratorService = zipGeneratorService;
    }

    @GetMapping
    public ResponseEntity<Flux<byte[]>> download(
        @RequestParam(required = false) Optional<Boolean> onFailure
    ) {
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"a-zip.zip\"")
            .body(zipGeneratorService.execute(onFailure.orElse(Boolean.FALSE)));
    }
}
