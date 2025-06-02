package cmahy.simple.spring.webapp.authorization.bet;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JwksGeneratorBetTest {

    public static void main(String[] args) {
        try {
            String publicKeyPem = """
                -----BEGIN PUBLIC KEY-----
                ....
                -----END PUBLIC KEY-----
                """;

            String base64 = publicKeyPem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
            byte[] decoded = Base64.getDecoder().decode(base64);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);

            RSAKey rsaJWK = new RSAKey.Builder(publicKey)
                .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
                .algorithm(com.nimbusds.jose.JWSAlgorithm.RS256)
                .keyID("taco-shop-m2m-jwt") // Doit matcher le `kid` dans le JWT signé
                .build();

            System.out.println(rsaJWK.toPublicJWK().toJSONString());
        } catch (Exception any) {
            any.printStackTrace();
        }
    }
}
