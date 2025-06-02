package cmahy.simple.spring.webapp.authorization.adapter.oauth2;

import cmahy.simple.spring.webapp.authorization.application.query.GetRightsByUsernameQuery;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OAuth2TokenCustomizerImpl implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final GetRightsByUsernameQuery getRightsByUsernameQuery;

    public OAuth2TokenCustomizerImpl(
        GetRightsByUsernameQuery getRightsByUsernameQuery
    ) {
        this.getRightsByUsernameQuery = getRightsByUsernameQuery;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.getValue().equals(context.getTokenType().getValue())) {
            try {
                Set<String> rightNames = getRightsByUsernameQuery.execute(context.getPrincipal().getName());

                if (!rightNames.isEmpty()) {
                    context.getClaims().claim("resources_authorities", rightNames);
                }
            } catch (Exception ignored) { }
        }
    }

}
