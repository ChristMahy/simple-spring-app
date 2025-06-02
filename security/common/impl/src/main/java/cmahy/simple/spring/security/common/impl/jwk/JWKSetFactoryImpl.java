//package cmahy.simple.spring.security.impl.jwk;
//
//import cmahy.simple.spring.security.api.jwk.JWKSetFactory;
//import cmahy.simple.spring.security.api.jwk.exception.JWKLoadingException;
//import cmahy.simple.spring.security.api.rsa.exception.RSAPublicKeyException;
//import cmahy.simple.spring.security.api.rsa.repository.RSAPublicKeyRepository;
//import cmahy.simple.spring.security.api.rsa.vo.id.PublicKeyId;
//import com.nimbusds.jose.jwk.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.security.interfaces.RSAPublicKey;
//import java.util.*;
//
//public class JWKSetFactoryImpl implements JWKSetFactory {
//
//    private static final Logger LOG = LoggerFactory.getLogger(JWKSetFactoryImpl.class);
//
//    private final RSAPublicKeyRepository rsaPublicKeyRepository;
//
//    public JWKSetFactoryImpl(RSAPublicKeyRepository rsaPublicKeyRepository) {
//        this.rsaPublicKeyRepository = rsaPublicKeyRepository;
//    }
//
//    @Override
//    public JWKSet create() throws JWKLoadingException {
//
//        try {
//
//            LOG.info("Creating JWKSet");
//
//            Map<PublicKeyId, RSAPublicKey> publicKeys = rsaPublicKeyRepository.findAll();
//
//            List<JWK> jwkKeys = new ArrayList<>(publicKeys.size());
//
//            for (Map.Entry<PublicKeyId, RSAPublicKey> entry : publicKeys.entrySet()) {
//
//                jwkKeys.add(
//                    new RSAKey.Builder(entry.getValue())
//                        .keyID(entry.getKey().id())
//                        .build()
//                );
//
//            }
//
//            return new JWKSet(jwkKeys);
//
//        } catch (RSAPublicKeyException e) {
//            throw new JWKLoadingException("JWKSet generation fails", e);
//        }
//
//    }
//
//}
