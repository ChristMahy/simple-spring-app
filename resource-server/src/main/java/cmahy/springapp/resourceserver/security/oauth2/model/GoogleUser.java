package cmahy.springapp.resourceserver.security.oauth2.model;

import cmahy.springapp.resourceserver.security.common.AuthProvider;

import java.util.Map;

public class GoogleUser extends OAuth2UserInfo {
    public GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }


    @Override
    public String getFirstName() {
        return (String)attributes.get("first_name");
    }


    @Override
    public String getLastName() {
        return (String)attributes.get("last_name");
    }

    @Override
    public String getId() {
        return (String)attributes.get("id");
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.GOOGLE;
    }
}
