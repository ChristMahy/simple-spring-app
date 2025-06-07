package cmahy.simple.spring.security.client.impl.webclient.jwt;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;
import cmahy.simple.spring.security.client.api.webclient.jwt.PrivateKeyJwtRequestParametersConverter;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

public class RSAPrivateKeyJwtRequestParametersConverter implements PrivateKeyJwtRequestParametersConverter {

    protected static final String ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";

    private static final Logger LOG = LoggerFactory.getLogger(RSAPrivateKeyJwtRequestParametersConverter.class);

    private final RSAPrivateKeyRepository rsaPrivateKeyRepository;

    public RSAPrivateKeyJwtRequestParametersConverter(RSAPrivateKeyRepository rsaPrivateKeyRepository) {
        this.rsaPrivateKeyRepository = rsaPrivateKeyRepository;
    }

    @Override
    public MultiValueMap<String, String> convert(OAuth2ClientCredentialsGrantRequest grantRequest) {

        ClientRegistration clientRegistration = grantRequest.getClientRegistration();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        try {

            String clientAssertion = createClientAssertion(clientRegistration);

            parameters.add("grant_type", CLIENT_CREDENTIALS.getValue());
            parameters.add("client_id", clientRegistration.getClientId());
            parameters.add("client_assertion_type", ASSERTION_TYPE);
            parameters.add("client_assertion", clientAssertion);

        } catch (RSAPrivateKeyException | JOSEException e) {
            LOG.warn("Can not generate client assertion", e);
        }

        return parameters;

    }

    private String createClientAssertion(ClientRegistration clientRegistration) throws RSAPrivateKeyException, JOSEException {

        RSAPrivateKey privateKey = rsaPrivateKeyRepository.findById(new PrivateKeyId(clientRegistration.getClientId()));

        JWSSigner signer = new RSASSASigner(privateKey);

        String clientId = clientRegistration.getClientId();
        String tokenUrl = clientRegistration.getProviderDetails().getTokenUri();

        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
            .issuer(clientId)
            .subject(clientId)
            .audience(tokenUrl)
            .issueTime(Date.from(now))
            .expirationTime(Date.from(now.plusSeconds(300)))
            .jwtID(UUID.randomUUID().toString())
            .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
            .keyID(clientId)
            .type(JOSEObjectType.JWT)
            .build();

        SignedJWT signedJWT = new SignedJWT(header, claims);
        signedJWT.sign(signer);

        return signedJWT.serialize();

    }

}
