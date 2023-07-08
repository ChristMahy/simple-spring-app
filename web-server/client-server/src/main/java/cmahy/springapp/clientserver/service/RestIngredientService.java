package cmahy.springapp.clientserver.service;

import cmahy.springapp.clientserver.domain.Ingredient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class RestIngredientService {
    private final WebClient oauth2WebClient;

    public RestIngredientService(
        WebClient oauth2WebClient
    ) {
        this.oauth2WebClient = oauth2WebClient;
    }

    public Flux<Ingredient> getAll() {
        return oauth2WebClient.get().uri("/api/ingredient")
            .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Ingredient.class));
    }
}
