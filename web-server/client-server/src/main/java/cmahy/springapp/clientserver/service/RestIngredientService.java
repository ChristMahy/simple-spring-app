package cmahy.springapp.clientserver.service;

import cmahy.springapp.clientserver.domain.Ingredient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestIngredientService {
    private final RestTemplate oauth2RestTemplate;

    public RestIngredientService(
        RestTemplate oauth2RestTemplate
    ) {
        this.oauth2RestTemplate = oauth2RestTemplate;
    }

    public Iterable<Ingredient> getAll() {
        return Arrays.asList(oauth2RestTemplate.getForObject(
            "http://resourceserver:8080/api/ingredient",
            Ingredient[].class
        ));
    }
}
