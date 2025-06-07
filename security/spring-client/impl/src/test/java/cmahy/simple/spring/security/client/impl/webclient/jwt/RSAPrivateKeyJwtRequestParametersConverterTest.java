package cmahy.simple.spring.security.client.impl.webclient.jwt;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.MultiValueMap;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.*;

import static cmahy.simple.spring.security.client.impl.webclient.jwt.RSAPrivateKeyJwtRequestParametersConverter.ASSERTION_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

@ExtendWith(MockitoExtension.class)
class RSAPrivateKeyJwtRequestParametersConverterTest {

    @Mock
    private RSAPrivateKeyRepository rsaPrivateKeyRepository;

    @InjectMocks
    private RSAPrivateKeyJwtRequestParametersConverter rsaPrivateKeyJwtRequestParametersConverter;

    private List<RSAPrivateKey> privatesKeys;

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> {
            int[] keySizes = new int[] {2048, 4096};
            privatesKeys = new ArrayList<>(keySizes.length);

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            for (int keySize : keySizes) {

                keyPairGenerator.initialize(keySize);

                KeyPair keyPair = keyPairGenerator.generateKeyPair();

                privatesKeys.add((RSAPrivateKey) keyPair.getPrivate());

            }
        });
    }

    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            for (RSAPrivateKey privateKey : privatesKeys) {

                Date now = Date.from(Instant.now().minusSeconds(1));

                OAuth2ClientCredentialsGrantRequest grantRequest = mock(OAuth2ClientCredentialsGrantRequest.class);
                ClientRegistration clientRegistration = mock(ClientRegistration.class);
                ClientRegistration.ProviderDetails providerDetails = mock(ClientRegistration.ProviderDetails.class);
                String clientId = Generator.generateAString();
                String tokenUri = Generator.generateAStringWithoutSpecialChars();

                when(grantRequest.getClientRegistration()).thenReturn(clientRegistration);

                when(clientRegistration.getClientId()).thenReturn(clientId);
                when(clientRegistration.getProviderDetails()).thenReturn(providerDetails);
                when(providerDetails.getTokenUri()).thenReturn(tokenUri);

                when(rsaPrivateKeyRepository.findById(eq(new PrivateKeyId(clientId)))).thenReturn(privateKey);


                MultiValueMap<String, String> actual = rsaPrivateKeyJwtRequestParametersConverter.convert(grantRequest);


                assertThat(actual)
                    .isNotNull()
                    .hasSize(4);

                assertThat(actual)
                    .containsOnlyKeys("grant_type", "client_id", "client_assertion_type", "client_assertion");

                assertThat(actual)
                    .extractingByKey("client_id", InstanceOfAssertFactories.LIST)
                    .containsExactly(clientId);

                assertThat(actual)
                    .extractingByKey("client_assertion", InstanceOfAssertFactories.LIST)
                    .hasSize(1)
                    .allMatch(value -> value instanceof String && StringUtils.isNotBlank((String) value));

                assertThat(actual.get("client_assertion").stream().findFirst())
                    .isNotEmpty()
                    .get()
                    .satisfies(clientAssertion -> {
                        SignedJWT jwt = SignedJWT.parse(clientAssertion);

                        JWSHeader header = jwt.getHeader();

                        assertThat(header.getAlgorithm()).isEqualTo(JWSAlgorithm.RS256);
                        assertThat(header.getType()).isEqualTo(JOSEObjectType.JWT);
                        assertThat(header.getKeyID()).isEqualTo(clientId);

                        JWTClaimsSet jwtClaims = jwt.getJWTClaimsSet();

                        assertThat(jwtClaims.getIssuer()).isEqualTo(clientId);
                        assertThat(jwtClaims.getSubject()).isEqualTo(clientId);
                        assertThat(jwtClaims.getAudience()).containsExactly(tokenUri);
                        assertThat(jwtClaims.getIssueTime()).isAfter(now);
                        assertThat(jwtClaims.getExpirationTime()).isAfter(now);
                        assertThat(jwtClaims.getJWTID()).isNotBlank();
                    });


                assertThat(actual)
                    .extractingByKey("client_assertion_type", InstanceOfAssertFactories.LIST)
                    .hasSize(1)
                    .allMatch(ASSERTION_TYPE::equals);

                assertThat(actual)
                    .extractingByKey("grant_type", InstanceOfAssertFactories.LIST)
                    .hasSize(1)
                    .allMatch(CLIENT_CREDENTIALS.getValue()::equals);

            }
        });
    }



    @Test
    void convert_whenUnsuccessfulAssertionGeneration_thenParametersHaveToBeEmpty() {
        assertDoesNotThrow(() -> {

            OAuth2ClientCredentialsGrantRequest grantRequest = mock(OAuth2ClientCredentialsGrantRequest.class);
            ClientRegistration clientRegistration = mock(ClientRegistration.class);
            String clientId = Generator.generateAString();

            when(grantRequest.getClientRegistration()).thenReturn(clientRegistration);

            when(clientRegistration.getClientId()).thenReturn(clientId);

            when(rsaPrivateKeyRepository.findById(eq(new PrivateKeyId(clientId)))).thenThrow(RSAPrivateKeyException.class);


            MultiValueMap<String, String> actual = rsaPrivateKeyJwtRequestParametersConverter.convert(grantRequest);


            assertThat(actual)
                .isNotNull()
                .isEmpty();

        });
    }
}