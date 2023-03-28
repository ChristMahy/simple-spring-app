package cmahy.springapp.resourceserver.security.oauth2.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoogleProfile {
    private String id;
    private String name;
    private String email;
    private String first_name;
    private String last_name;
}
