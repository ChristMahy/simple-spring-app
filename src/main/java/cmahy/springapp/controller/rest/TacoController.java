package cmahy.springapp.controller.rest;

import cmahy.springapp.domain.Taco;
import cmahy.springapp.repository.TacoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

@RestController
@RequestMapping(
    path = "/api/tacos",
    produces = { APPLICATION_JSON_VALUE, TEXT_XML_VALUE }
)
public class TacoController {
    private final TacoRepository tacoRepository;

    public TacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        Pageable pageable = PageRequest.of(
            0, 12, Sort.by("createdAt").descending()
        );

        return tacoRepository.findAll(pageable).getContent();
    }
}
