package cmahy.simple.spring.webapp.taco.shop.adapter.webclient.config.properties.webclient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.nio.charset.StandardCharsets;

public record WebClientTacoCredentialProperties(
    @NotNull(message = "Shouldn't be null")
    @NotEmpty(message = "Shouldn't be empty")
    byte[] username,
    @NotNull(message = "Shouldn't be null")
    @NotEmpty(message = "Shouldn't be empty")
    byte[] password
) {

    public String usernameToString() {
        return new String(username(), StandardCharsets.UTF_8);
    }

    public String passwordToString() {
        return new String(password(), StandardCharsets.UTF_8);
    }
}
