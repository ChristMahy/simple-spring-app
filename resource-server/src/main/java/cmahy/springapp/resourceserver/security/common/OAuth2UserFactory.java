package cmahy.springapp.resourceserver.security.common;

import cmahy.springapp.resourceserver.security.oauth2.api.Google;
import cmahy.springapp.resourceserver.security.oauth2.model.GoogleUser;
import cmahy.springapp.resourceserver.security.oauth2.model.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.HashMap;
import java.util.Map;

public final class OAuth2UserFactory {
    private OAuth2UserFactory() {}

    public static OAuth2UserInfo getOAuth2User(OAuth2UserRequest request) {
        if (AuthProvider.GOOGLE.name().equalsIgnoreCase(request.getClientRegistration().getRegistrationId())) {
            var google = new Google(request.getAccessToken().getTokenValue());
            var profile = google.getProfileDetails();

            System.out.println(profile);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("id", profile.getSub());
            attributes.put("name", profile.getName());
            attributes.put("firstName", profile.getGiven_name());
            attributes.put("lastName", profile.getFamily_name());
            attributes.put("email", profile.getEmail());

            return new GoogleUser(attributes);
        }

        throw new RuntimeException("Login with " + request.getClientRegistration().getRegistrationId() + " not supported");
    }
}
