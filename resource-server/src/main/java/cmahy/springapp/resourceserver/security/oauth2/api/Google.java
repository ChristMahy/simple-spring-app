package cmahy.springapp.resourceserver.security.oauth2.api;

import cmahy.springapp.resourceserver.security.oauth2.model.GoogleProfile;

public class Google extends ApiBinding {
    private static final String GOOGLE_GRAPH_BASE_URL ="https://www.googleapis.com/oauth2/v3/userinfo";

    // Leaving this blank in case you plan to integrate Google Provider for OAuth2
    //Also please check CommonOAuth2Provider class for Base URL of google/facebook/github/okta
    public Google(String accessToken) {
        super(accessToken);
    }

    public GoogleProfile getProfileDetails() {
        System.out.println("Fetching facebook profile details");
        return restTemplate.getForObject(GOOGLE_GRAPH_BASE_URL, GoogleProfile.class);
    }
}
