package cmahy.springapp.resourceserver.security.oauth2.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoogleProfile {
    private String sub;
    private String name;
    private String email;
    private String given_name;
    private String family_name;
}
