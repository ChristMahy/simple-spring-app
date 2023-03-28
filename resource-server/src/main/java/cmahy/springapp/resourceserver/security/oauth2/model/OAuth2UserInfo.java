package cmahy.springapp.resourceserver.security.oauth2.model;

import cmahy.springapp.resourceserver.security.common.AuthProvider;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getEmail();
    public abstract String getFirstName();
    public abstract String getLastName();
    public abstract String getId();
    public abstract AuthProvider getProvider();
}
